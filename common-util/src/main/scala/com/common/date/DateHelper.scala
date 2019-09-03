package com.common.date

import java.sql.Timestamp

import com.common.logger.Logging
import org.joda.time.{DateTime, DateTimeZone, LocalDate}
import org.joda.time.format.DateTimeFormat

import scala.util.{Failure, Success, Try}


trait DateHelper extends Logging{

  lazy val dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
  private val dateFormats: Seq[String] = Seq("yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
    "yyyy-MM-dd'T'HH:mm:ssZZ", "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ss-SSSS", "yyyy-MM-dd'T'HH:mm:ss+SSSS", "yyyy-MM-dd'T'HH:mmZ")

  implicit def dateTimeOrdering: Ordering[DateTime] = Ordering.fromLessThan(_ isBefore _)

  def dateTimeOrderingReverse: Ordering[DateTime] = Ordering.fromLessThan(_ isAfter _)

  def getCurrentDateTime: DateTime = new DateTime(getCurrentTimeInMillis)

  def getCurrentTimeInMillis: Long = System.currentTimeMillis()

  def getCurrentLocalDate: LocalDate = new LocalDate()

  def getLocalDate(dateString: String): LocalDate = getDateTime(dateString).toLocalDate

  def getDateTime(dateString: String): DateTime = {
    DateTimeZone.setDefault(DateTimeZone.UTC)
    def getDateTime(dateFormats: Seq[String], dateString: String): DateTime = {
      try {
        val dtFormat = DateTimeFormat.forPattern(dateFormats.head)
        dtFormat.parseDateTime(dateString)
      } catch {
        case _ if dateFormats.nonEmpty => getDateTime(dateFormats.tail, dateString)
        case ex: Exception =>
          throw new IllegalArgumentException("Unknown date format -> " + dateString, ex)
      }
    }
    getDateTime(dateFormats, dateString)
  }

  def parseLocalDateTime(dateTime: String): Option[DateTime] = {
    Try(dtf.parseDateTime(dateTime)) match {
      case Success(value) => Some(value)
      case Failure(ex) =>
        error("could not parse to date time", ex)
        None
    }
  }

  def getCurrentTimestamp: Timestamp = new Timestamp(getCurrentTimeInMillis)


  def getDateTime(dateString: String, fromDateTimeZone: DateTimeZone, toDateTimeZone: DateTimeZone): DateTime = {
    def getDateTime(dateFormats: Seq[String], dateString: String): DateTime = {
      try {
        val dtFormat = DateTimeFormat.forPattern(dateFormats.head)
        val mid = dtFormat.parseDateTime(dateString).withZoneRetainFields(fromDateTimeZone)
        new DateTime(mid).withZone(toDateTimeZone)
      } catch {
        case _ if dateFormats.nonEmpty => getDateTime(dateFormats.tail, dateString)
        case ex: Exception =>
          throw new IllegalArgumentException("Unknown date format -> " + dateString, ex)
      }
    }
    getDateTime(dateFormats, dateString)
  }

  def getDateTimeHHmm(dateString: String): Option[String] = {
    def getDateTime(dateFormats: Seq[String], dateString: String): Option[String] = {
      try {
        val dtf2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
        val dtFormat = DateTimeFormat.forPattern(dateFormats.head)
        val mid = dtFormat.parseLocalDateTime(dateString)
        Some(dtf2.print(mid))
      } catch {
        case _ if dateFormats.nonEmpty => getDateTime(dateFormats.tail, dateString)
        case ex: Exception =>
          warn("Unknown date format -> " + dateString, ex)
          None
      }
    }
    getDateTime(dateFormats, dateString)
  }

  def dateTimeToTimestamp(dateTime: DateTime): Timestamp = {
    new Timestamp(dateTime.getMillis)
  }

  def timestampToJodaDate(timestamp: Option[Timestamp]): Option[DateTime] = {
    timestamp map(time => new DateTime(time.getTime))
  }

}

object DateHelper extends DateHelper

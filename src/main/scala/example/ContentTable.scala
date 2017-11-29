package ophan.model

import cats.syntax.either._

import ophan.thrift.event.{Tag => OphanTag}
import ophan.thrift.event.{MediaType => EventMediaType}
import org.joda.time.{DateTime, DateTimeZone, Duration, LocalDate}
import com.gu.scanamo._
import com.gu.scanamo.syntax._
import org.joda.time.format.DateTimeFormat

import scala.collection.JavaConversions._
import scala.util.Try


case class CAPIPodcast()

case class CAPITag(
  id: String,
  `type`: String,
  sectionId: Option[String],
  sectionName: Option[String],
  webTitle: Option[String],
  webUrl: Option[String],
  podcast: Option[CAPIPodcast]
)


case class CAPINewspaperInfo(
  publicationDate: Long,
  publication: String,
  book: String,
  bookSection: String,
  pageNumber: Int
)

case class CAPIMedia(
  mediaType: EventMediaType,
  id: String
)

object CAPIMedia {
  implicit val capiMediaTypeFormat = DynamoFormat.coercedXmap[EventMediaType, Int, IllegalArgumentException](
    EventMediaType.getOrUnknown
  )(_.value)

}

case class CAPIContent(
  webUrl: String,
  host: Option[String],
  tags: List[CAPITag],
  sectionId: Option[String],
  sectionName: Option[String],
  webTitle: String,
  newspaper: Option[CAPINewspaperInfo],
  altTextOfMainImage: Option[String],
  headline: Option[String],
  standfirst: Option[String],
  trailText: Option[String],
  wordcount: Option[Int],
  commentable: Option[Boolean],
  creationDate: Option[DateTime],
  lastModified: Option[DateTime],
  firstPublicationDate: Option[DateTime],
  shortUrl: Option[String],
  thumbnail: Option[String],
  legallySensitive: Option[Boolean],
  sensitive: Option[Boolean],
  productionOffice: Option[String],
  shouldHideAdverts: Option[Boolean],
  webPublicationDate: Option[DateTime],
  webPublicationDay: Option[String],
  internalLinkCount: Option[Int],
  internalComposerCode: Option[String],
  duration: Option[Duration],
  mediaElements: Option[List[CAPIMedia]],
  isCommentable: Option[Boolean],
  shortUrlPath: Option[String]
) {
}

object CAPIContent {
  import com.gu.scanamo._
  import com.gu.scanamo.syntax._
  import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._

  implicit val durationFormat = DynamoFormat.coercedXmap[Duration, Long, IllegalArgumentException](
    Duration.millis
  )(_.getMillis)
  implicit val utcDateTimeFormat = DynamoFormat.coercedXmap[DateTime, Long, IllegalArgumentException](
    new DateTime(_).withZone(DateTimeZone.UTC)
  )(_.getMillis)



}

object ContentTable {
  // imports needed for Scanamo
  import com.gu.scanamo._
  import com.gu.scanamo.syntax._
  import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType._
  // import CAPIMedia.capiMediaTypeFormat
  import CAPIContent.durationFormat
  import CAPIContent.utcDateTimeFormat

  lazy val tableName = "foo"
  lazy val table = Table[CAPIContent](tableName)
}

package br.com.sigmaonline.echojournal.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.sigmaonline.echojournal.core.database.echo.EchoDao
import br.com.sigmaonline.echojournal.core.database.echo.EchoEntity
import br.com.sigmaonline.echojournal.core.database.echo.FloatListTypeConverter
import br.com.sigmaonline.echojournal.core.database.echo.MoodTypeConverter
import br.com.sigmaonline.echojournal.core.database.echo_topic_relation.EchoTopicCrossRef
import br.com.sigmaonline.echojournal.core.database.topic.TopicEntity

@Database(
    entities = [EchoEntity::class, TopicEntity::class, EchoTopicCrossRef::class],
    version = 1,
)
@TypeConverters(
    MoodTypeConverter::class,
    FloatListTypeConverter::class
)
abstract class EchoDatabase : RoomDatabase() {
    abstract val echoDao: EchoDao
}
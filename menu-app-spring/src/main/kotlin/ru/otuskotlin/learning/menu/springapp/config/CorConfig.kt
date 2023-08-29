package ru.otuskotlin.learning.menu.springapp.config

import GoodsRepoStub
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsCorSettings
import ru.otuskotlin.learning.menu.common.repo.IGoodsRepository
import ru.otuskotlin.learning.menu.repo.sql.RepoGoodsSQL
import ru.otuskotlin.learning.menu.repo.sql.SqlProperties
import ru.otuskotlin.learning.repo.inmemory.GoodsRepoInMemory

@Configuration
@EnableConfigurationProperties(SqlPropertiesEx::class)
class CorConfig {

    @Bean
    fun prodRepository(sqlProperties: SqlProperties) = RepoGoodsSQL(sqlProperties)

    @Bean
    fun testRepository() = GoodsRepoInMemory()

    @Bean
    fun stubRepository() = GoodsRepoStub()

    @Bean
    fun corSettings(
        @Qualifier("prodRepository") prodRepository: IGoodsRepository,
        @Qualifier("testRepository") testRepository: IGoodsRepository,
        @Qualifier("stubRepository") stubRepository: IGoodsRepository,
    ): GoodsCorSettings = GoodsCorSettings(
        repoStub = stubRepository,
        repoTest = testRepository,
        repoProd = prodRepository,
    )

    @Bean
    fun processor(goodsCorSettings: GoodsCorSettings ) = GoodsProcessor(goodsCorSettings)
}
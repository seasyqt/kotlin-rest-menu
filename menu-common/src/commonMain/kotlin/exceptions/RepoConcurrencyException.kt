package ru.otuskotlin.learning.menu.common.exceptions

import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock

class RepoConcurrencyException(expectedLock: GoodsLock, actualLock: GoodsLock?) : RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)


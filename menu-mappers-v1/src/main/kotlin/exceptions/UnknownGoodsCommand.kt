package exceptions

import models.goods.GoodsCommand

class UnknownGoodsCommand(cmd: GoodsCommand) : Throwable("Wrong command $cmd at mapping toTransport")


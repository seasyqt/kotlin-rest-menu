package exceptions

import models.order.OrderCommand

class UnknownOrderCommand(cmd: OrderCommand) : Throwable("Wrong command $cmd at mapping toTransport")


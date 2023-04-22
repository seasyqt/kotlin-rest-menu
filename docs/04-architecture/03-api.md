# API

## Сущности

1. Блюдо/продукт - Goods 
2. Список заказов и их статус - Order

## Описание сущности Goods

1. Id (Int)
2. Name (VARCHAR (512))
3. Type (Enum)
4. Price (Int)
5. Weight (VARCHAR (512))

## Описание сущности Order

1. Id (Int)
2. GoodsList (List)
   1. Goods
      1. Id (Int)
      2. Name (VARCHAR (512))
      3. Price (Int)
      4. Count (Int)
   2. Goods
      1. Id (Int)
      2. Name (VARCHAR (512))
      3. Price (Int)
      4. Count (Int)</br>
   ...
3. CreatedDate (TIMESTAMP)
4. TotalPrice (Int)
5. BuyerId (Int)

## Функции (эндпониты)

1. Goods CRUDS
   1. create
   2. read
   3. update
   4. delete
   5. search - поиск по фильтрам
2. Order CRUD
   1. create
   2. read
   3. update
   4. delete
   5. search - поиск по фильтрам

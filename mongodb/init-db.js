db.createCollection("section")
db.section.insert(
  [
    {
      publicId : "83dda947-a4ee-4676-b929-13b52f9e0426",
      name : "napoje"
    },
    {
      publicId : "d557db5a-a911-4a5c-92d5-feb1293f5fba",
      name : "jedzenie"
    }
  ]
)
db.createCollection("category")
db.category.insert(
  [
    {
      publicId : "8911af58-b945-4bf2-b153-8ace71db86b2",
      name : "woda"
    },
    {
      publicId : "23d1ca96-4b4a-4176-ab44-2f56622007ee",
      name : "ryba"
    }
  ]
)
db.createCollection("product")
db.product.insert(
  [
    {
      publicId : "648fca2c-9aeb-4773-8d15-536927f44cc0",
      name : "Makrela wędzona",
      price : 1450,
      productionDate : ISODate("2019-04-12"),
      bestBefore : ISODate("2019-04-29"),
      sectionPublicId : "d557db5a-a911-4a5c-92d5-feb1293f5fba",
      categoryPublicId : "23d1ca96-4b4a-4176-ab44-2f56622007ee"
    },
    {
      publicId : "22f77e87-abff-4946-a9e7-457ade8af197",
      name : "Polędwica z dorsza",
      price : 6599,
      productionDate : ISODate("2019-04-12"),
      bestBefore : ISODate("2019-04-29"),
      sectionPublicId : "d557db5a-a911-4a5c-92d5-feb1293f5fba",
      categoryPublicId : "23d1ca96-4b4a-4176-ab44-2f56622007ee"
    },
    {
      publicId : "20da8e84-d189-4e63-9875-fe9855f04c39",
      name : "Łosoś wędzony",
      price : 4399,
      productionDate : ISODate("2019-04-12"),
      bestBefore : ISODate("2019-04-29"),
      sectionPublicId : "d557db5a-a911-4a5c-92d5-feb1293f5fba",
      categoryPublicId : "23d1ca96-4b4a-4176-ab44-2f56622007ee"
    },
    {
      publicId : "9e1eadd1-824d-4722-af12-397dab2a9c81",
      name : "Kropla Beskidu",
      price : 199,
      productionDate : ISODate("2019-04-12"),
      bestBefore : ISODate("2019-04-29"),
      sectionPublicId : "83dda947-a4ee-4676-b929-13b52f9e0426",
      categoryPublicId : "8911af58-b945-4bf2-b153-8ace71db86b2"
    },
    {
      publicId : "8067993a-b21f-4a40-b47f-b365743f95b9",
      name : "Nałęczowianka",
      price : 219,
      productionDate : ISODate("2019-04-12"),
      bestBefore : ISODate("2019-04-29"),
      sectionPublicId : "83dda947-a4ee-4676-b929-13b52f9e0426",
      categoryPublicId : "8911af58-b945-4bf2-b153-8ace71db86b2"
    },
    {
      publicId : "a98c5843-b9e2-448f-9c10-2c8626dcea1e",
      name : "Muszynianka",
      price : 210,
      productionDate : ISODate("2019-04-12"),
      bestBefore : ISODate("2019-04-29"),
      sectionPublicId : "83dda947-a4ee-4676-b929-13b52f9e0426",
      categoryPublicId : "8911af58-b945-4bf2-b153-8ace71db86b2"
    }
  ]
)
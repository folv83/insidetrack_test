SET DEFINE OFF;


--Table of Cities
CREATE TABLE ITD_MT_CITY
(
	ID_CITY		NUMBER(38,0) NOT NULL,
	CO_CITY		VARCHAR2(10) NOT NULL,
	CONSTRAINT ITD_MT_CITY_PK PRIMARY KEY(ID_CITY)
);

COMMENT ON TABLE ITD_MT_CITY IS 'Cities';
COMMENT ON COLUMN ITD_MT_CITY.ID_CITY IS 'ID of the city. Sequence: ITD_SEQ_CITY';
COMMENT ON COLUMN ITD_MT_CITY.CO_CITY IS 'Code of the city';


--Table of Customers Root
CREATE TABLE ITD_MT_CUSTOMER_ROOT
(
	ID_CUSTOMER_ROOT	NUMBER(38,0) NOT NULL,
	NA_CUSTOMER_ROOT	VARCHAR2(100) NOT NULL,
	CONSTRAINT ITD_MT_CUSTOMER_ROOT_PK PRIMARY KEY(ID_CUSTOMER_ROOT)
);

COMMENT ON TABLE ITD_MT_CUSTOMER_ROOT IS 'Customers Root';
COMMENT ON COLUMN ITD_MT_CUSTOMER_ROOT.ID_CUSTOMER_ROOT IS 'ID of the customer root. Sequence: ITD_SEQ_CUSTOMER_ROOT';
COMMENT ON COLUMN ITD_MT_CUSTOMER_ROOT.NA_CUSTOMER_ROOT IS 'Name of the customer root';


--Table of Customers Leaf
CREATE TABLE ITD_MT_CUSTOMER_LEAF
(
	ID_CUSTOMER_LEAF	NUMBER(38,0) NOT NULL,
	ID_CUSTOMER_ROOT	NUMBER(38,0) NOT NULL,
	ID_CITY				NUMBER(38,0) NOT NULL,
	CONSTRAINT ITD_MT_CUSTOMER_LEAF_PK PRIMARY KEY(ID_CUSTOMER_LEAF)
);
ALTER TABLE ITD_MT_CUSTOMER_LEAF
	ADD CONSTRAINT ITD_CULE_REF_CURO_FK
	FOREIGN KEY(ID_CUSTOMER_ROOT) REFERENCES ITD_MT_CUSTOMER_ROOT(ID_CUSTOMER_ROOT);
ALTER TABLE ITD_MT_CUSTOMER_LEAF
	ADD CONSTRAINT ITD_CULE_REF_CITY_FK
	FOREIGN KEY(ID_CITY) REFERENCES ITD_MT_CITY(ID_CITY);

COMMENT ON TABLE ITD_MT_CUSTOMER_LEAF IS 'Customers Leaf';
COMMENT ON COLUMN ITD_MT_CUSTOMER_LEAF.ID_CUSTOMER_LEAF IS 'ID of the customer leaf. Sequence: ITD_SEQ_CUSTOMER_LEAF';
COMMENT ON COLUMN ITD_MT_CUSTOMER_LEAF.ID_CUSTOMER_ROOT IS 'ID of the customer root. Foreign key towards the table ITD_MT_CUSTOMER_ROOT';
COMMENT ON COLUMN ITD_MT_CUSTOMER_LEAF.ID_CITY IS 'ID of the city. Foreign key towards the table ITD_MT_CITY';


--Table of Distributors Root
CREATE TABLE ITD_MT_DISTRIBUTOR_ROOT
(
	ID_DISTRIBUTOR_ROOT		NUMBER(38,0) NOT NULL,
	NA_DISTRIBUTOR_ROOT		VARCHAR2(100) NOT NULL,
	CONSTRAINT ITD_MT_DISTRIBUTOR_ROOT_PK PRIMARY KEY(ID_DISTRIBUTOR_ROOT)
);

COMMENT ON TABLE ITD_MT_DISTRIBUTOR_ROOT IS 'Distributors Root';
COMMENT ON COLUMN ITD_MT_DISTRIBUTOR_ROOT.ID_DISTRIBUTOR_ROOT IS 'ID of the distributor root. Sequence: ITD_SEQ_DISTRIBUTOR_ROOT';
COMMENT ON COLUMN ITD_MT_DISTRIBUTOR_ROOT.NA_DISTRIBUTOR_ROOT IS 'Name of the distributor root';


--Table of Distributors Leaf
CREATE TABLE ITD_MT_DISTRIBUTOR_LEAF
(
	ID_DISTRIBUTOR_LEAF		NUMBER(38,0) NOT NULL,
	ID_DISTRIBUTOR_ROOT		NUMBER(38,0) NOT NULL,
	ID_CITY					NUMBER(38,0) NOT NULL,
	CONSTRAINT ITD_MT_DISTRIBUTOR_LEAF_PK PRIMARY KEY(ID_DISTRIBUTOR_LEAF)
);
ALTER TABLE ITD_MT_DISTRIBUTOR_LEAF
	ADD CONSTRAINT ITD_DILE_REF_DIRO_FK
	FOREIGN KEY(ID_DISTRIBUTOR_ROOT) REFERENCES ITD_MT_DISTRIBUTOR_ROOT(ID_DISTRIBUTOR_ROOT);
ALTER TABLE ITD_MT_DISTRIBUTOR_LEAF
	ADD CONSTRAINT ITD_DILE_REF_CITY_FK
	FOREIGN KEY(ID_CITY) REFERENCES ITD_MT_CITY(ID_CITY);

COMMENT ON TABLE ITD_MT_DISTRIBUTOR_LEAF IS 'Distributors Leaf';
COMMENT ON COLUMN ITD_MT_DISTRIBUTOR_LEAF.ID_DISTRIBUTOR_LEAF IS 'ID of the distributor leaf. Sequence: ITD_SEQ_DISTRIBUTOR_LEAF';
COMMENT ON COLUMN ITD_MT_DISTRIBUTOR_LEAF.ID_DISTRIBUTOR_ROOT IS 'ID of the distributor root. Foreign key towards the table ITD_MT_DISTRIBUTOR_ROOT';
COMMENT ON COLUMN ITD_MT_DISTRIBUTOR_LEAF.ID_CITY IS 'ID of the city. Foreign key towards the table ITD_MT_CITY';


--Table of Manufacturers
CREATE TABLE ITD_MT_MANUFACTURER
(
	ID_MANUFACTURER		NUMBER(38,0) NOT NULL,
	NA_MANUFACTURER		VARCHAR2(100) NOT NULL,
	CONSTRAINT ITD_MT_MANUFACTURER_PK PRIMARY KEY(ID_MANUFACTURER)
);

COMMENT ON TABLE ITD_MT_MANUFACTURER IS 'Manufacturers';
COMMENT ON COLUMN ITD_MT_MANUFACTURER.ID_MANUFACTURER IS 'ID of the manufacturer. Sequence: ITD_SEQ_MANUFACTURER';
COMMENT ON COLUMN ITD_MT_MANUFACTURER.NA_MANUFACTURER IS 'Name of the manufacturer';


--Table of Categories
CREATE TABLE ITD_MT_CATEGORY
(
	ID_CATEGORY		NUMBER(38,0) NOT NULL,
	NA_CATEGORY		VARCHAR2(100) NOT NULL,
	CONSTRAINT ITD_MT_CATEGORY_PK PRIMARY KEY(ID_CATEGORY)
);

COMMENT ON TABLE ITD_MT_CATEGORY IS 'Categories';
COMMENT ON COLUMN ITD_MT_CATEGORY.ID_CATEGORY IS 'ID of the category. Sequence: ITD_SEQ_CATEGORY';
COMMENT ON COLUMN ITD_MT_CATEGORY.NA_CATEGORY IS 'Name of the category';


--Table of Unit Types
CREATE TABLE ITD_MT_UNIT_TYPE
(
	ID_UNIT_TYPE	NUMBER(38,0) NOT NULL,
	NA_UNIT_TYPE	VARCHAR2(100) NOT NULL,
	CONSTRAINT ITD_MT_UNIT_TYPE_PK PRIMARY KEY(ID_UNIT_TYPE)
);

COMMENT ON TABLE ITD_MT_UNIT_TYPE IS 'Unit Types';
COMMENT ON COLUMN ITD_MT_UNIT_TYPE.ID_UNIT_TYPE IS 'ID of the unit type. Sequence: ITD_SEQ_UNIT_TYPE';
COMMENT ON COLUMN ITD_MT_UNIT_TYPE.NA_UNIT_TYPE IS 'Name of the unit type';


--Table of Pack Sizes
CREATE TABLE ITD_MT_PACK_SIZE
(
	ID_PACK_SIZE	NUMBER(38,0) NOT NULL,
	NA_PACK_SIZE	VARCHAR2(100) NOT NULL,
	CONSTRAINT ITD_MT_PACK_SIZE_PK PRIMARY KEY(ID_PACK_SIZE)
);

COMMENT ON TABLE ITD_MT_PACK_SIZE IS 'Pack Sizes';
COMMENT ON COLUMN ITD_MT_PACK_SIZE.ID_PACK_SIZE IS 'ID of the pack size. Sequence: ITD_SEQ_PACK_SIZE';
COMMENT ON COLUMN ITD_MT_PACK_SIZE.NA_PACK_SIZE IS 'Name of the pack size';


--Table of Products
CREATE TABLE ITD_MT_PRODUCT
(
	ID_PRODUCT			NUMBER(38,0) NOT NULL,
	DE_PRODUCT			VARCHAR2(100) NOT NULL,
	ID_MANUFACTURER		NUMBER(38,0) NOT NULL,
	ID_CATEGORY			NUMBER(38,0) NOT NULL,
	ID_UNIT_TYPE		NUMBER(38,0) NOT NULL,
	ID_PACK_SIZE		NUMBER(38,0) NOT NULL,
	AM_PRICE			NUMBER(10,2) NOT NULL,
	CONSTRAINT ITD_MT_PRODUCT_PK PRIMARY KEY(ID_PRODUCT)
);
ALTER TABLE ITD_MT_PRODUCT
	ADD CONSTRAINT ITD_PROD_REF_MANU_FK
	FOREIGN KEY(ID_MANUFACTURER) REFERENCES ITD_MT_MANUFACTURER(ID_MANUFACTURER);
ALTER TABLE ITD_MT_PRODUCT
	ADD CONSTRAINT ITD_PROD_REF_CATE_FK
	FOREIGN KEY(ID_CATEGORY) REFERENCES ITD_MT_CATEGORY(ID_CATEGORY);
ALTER TABLE ITD_MT_PRODUCT
	ADD CONSTRAINT ITD_PROD_REF_UNTY_FK
	FOREIGN KEY(ID_UNIT_TYPE) REFERENCES ITD_MT_UNIT_TYPE(ID_UNIT_TYPE);
ALTER TABLE ITD_MT_PRODUCT
	ADD CONSTRAINT ITD_PROD_REF_PASI_FK
	FOREIGN KEY(ID_PACK_SIZE) REFERENCES ITD_MT_PACK_SIZE(ID_PACK_SIZE);

COMMENT ON TABLE ITD_MT_PRODUCT IS 'Products';
COMMENT ON COLUMN ITD_MT_PRODUCT.ID_PRODUCT IS 'ID of the product. Sequence: ITD_SEQ_PRODUCT';
COMMENT ON COLUMN ITD_MT_PRODUCT.DE_PRODUCT IS 'Description of the product';
COMMENT ON COLUMN ITD_MT_PRODUCT.ID_MANUFACTURER IS 'ID of the manufacturer. Foreign key towards the table ITD_MT_MANUFACTURER';
COMMENT ON COLUMN ITD_MT_PRODUCT.ID_CATEGORY IS 'ID of the category. Foreign key towards the table ITD_MT_CATEGORY';
COMMENT ON COLUMN ITD_MT_PRODUCT.ID_UNIT_TYPE IS 'ID of the unit type. Foreign key towards the table ITD_MT_UNIT_TYPE';
COMMENT ON COLUMN ITD_MT_PRODUCT.ID_PACK_SIZE IS 'ID of the pack size. Foreign key towards the table ITD_MT_PACK_SIZE';
COMMENT ON COLUMN ITD_MT_PRODUCT.AM_PRICE IS 'Price of the product';


--Table of Purchases
CREATE TABLE ITD_MT_PURCHASE
(
	ID_PURCHASE			NUMBER(38,0) NOT NULL,
	CO_INVOICE			VARCHAR2(20) NOT NULL,
	DA_PURCHASE			DATE NOT NULL,
	ID_CUSTOMER_ROOT	NUMBER(38,0) NOT NULL,
	ID_CUSTOMER_LEAF	NUMBER(38,0) NOT NULL,
	NU_PRODUCTS			NUMBER(3,0) NOT NULL,
	AM_TOTAL			NUMBER(10,2) NOT NULL,
	CONSTRAINT ITD_MT_PURCHASE_PK PRIMARY KEY(ID_PURCHASE)
);
ALTER TABLE ITD_MT_PURCHASE
	ADD CONSTRAINT ITD_PURC_REF_CURO_FK
	FOREIGN KEY(ID_CUSTOMER_ROOT) REFERENCES ITD_MT_CUSTOMER_ROOT(ID_CUSTOMER_ROOT);
ALTER TABLE ITD_MT_PURCHASE
	ADD CONSTRAINT ITD_PURC_REF_CULE_FK
	FOREIGN KEY(ID_CUSTOMER_LEAF) REFERENCES ITD_MT_CUSTOMER_LEAF(ID_CUSTOMER_LEAF);

COMMENT ON TABLE ITD_MT_PURCHASE IS 'Purchases';
COMMENT ON COLUMN ITD_MT_PURCHASE.ID_PURCHASE IS 'ID of the purchase. Sequence: ITD_SEQ_PURCHASE';
COMMENT ON COLUMN ITD_MT_PURCHASE.CO_INVOICE IS 'Code of the invoice';
COMMENT ON COLUMN ITD_MT_PURCHASE.DA_PURCHASE IS 'Date of the purchase';
COMMENT ON COLUMN ITD_MT_PURCHASE.ID_CUSTOMER_ROOT IS 'ID of the customer root. Foreign key towards the table ITD_MT_CUSTOMER_ROOT';
COMMENT ON COLUMN ITD_MT_PURCHASE.ID_CUSTOMER_LEAF IS 'ID of the customer leaf. Foreign key towards the table ITD_MT_CUSTOMER_LEAF';
COMMENT ON COLUMN ITD_MT_PURCHASE.NU_PRODUCTS IS 'Number of products';
COMMENT ON COLUMN ITD_MT_PURCHASE.AM_TOTAL IS 'Total of the invoice';


--Table of Products per Purchase
CREATE TABLE ITD_MT_PRODUCT_PURCHASE
(
	ID_PRODUCT_PURCHASE		NUMBER(38,0) NOT NULL,
	ID_PURCHASE				NUMBER(38,0) NOT NULL,
	ID_PRODUCT				NUMBER(38,0) NOT NULL,
	ID_MANUFACTURER			NUMBER(38,0) NOT NULL,
	ID_CATEGORY				NUMBER(38,0) NOT NULL,
	ID_UNIT_TYPE			NUMBER(38,0) NOT NULL,
	ID_PACK_SIZE			NUMBER(38,0) NOT NULL,
	ID_DISTRIBUTOR_ROOT		NUMBER(38,0) NOT NULL,
	ID_DISTRIBUTOR_LEAF		NUMBER(38,0) NOT NULL,
	AM_QUANTITY				NUMBER(10,2) NOT NULL,
	AM_PRICE				NUMBER(10,2) NOT NULL,
	AM_TOTAL				NUMBER(10,2) NOT NULL,
	CONSTRAINT ITD_MT_PRODUCT_PURCHASE_PK PRIMARY KEY(ID_PRODUCT_PURCHASE)
);
ALTER TABLE ITD_MT_PRODUCT_PURCHASE
	ADD CONSTRAINT ITD_PRPU_REF_PURC_FK
	FOREIGN KEY(ID_PURCHASE) REFERENCES ITD_MT_PURCHASE(ID_PURCHASE);
ALTER TABLE ITD_MT_PRODUCT_PURCHASE
	ADD CONSTRAINT ITD_PRPU_REF_PROD_FK
	FOREIGN KEY(ID_PRODUCT) REFERENCES ITD_MT_PRODUCT(ID_PRODUCT);
ALTER TABLE ITD_MT_PRODUCT_PURCHASE
	ADD CONSTRAINT ITD_PRPU_REF_MANU_FK
	FOREIGN KEY(ID_MANUFACTURER) REFERENCES ITD_MT_MANUFACTURER(ID_MANUFACTURER);
ALTER TABLE ITD_MT_PRODUCT_PURCHASE
	ADD CONSTRAINT ITD_PRPU_REF_CATE_FK
	FOREIGN KEY(ID_CATEGORY) REFERENCES ITD_MT_CATEGORY(ID_CATEGORY);
ALTER TABLE ITD_MT_PRODUCT_PURCHASE
	ADD CONSTRAINT ITD_PRPU_REF_UNTY_FK
	FOREIGN KEY(ID_UNIT_TYPE) REFERENCES ITD_MT_UNIT_TYPE(ID_UNIT_TYPE);
ALTER TABLE ITD_MT_PRODUCT_PURCHASE
	ADD CONSTRAINT ITD_PRPU_REF_PASI_FK
	FOREIGN KEY(ID_PACK_SIZE) REFERENCES ITD_MT_PACK_SIZE(ID_PACK_SIZE);
ALTER TABLE ITD_MT_PRODUCT_PURCHASE
	ADD CONSTRAINT ITD_PRPU_REF_DIRO_FK
	FOREIGN KEY(ID_DISTRIBUTOR_ROOT) REFERENCES ITD_MT_DISTRIBUTOR_ROOT(ID_DISTRIBUTOR_ROOT);
ALTER TABLE ITD_MT_PRODUCT_PURCHASE
	ADD CONSTRAINT ITD_PRPU_REF_DILE_FK
	FOREIGN KEY(ID_DISTRIBUTOR_LEAF) REFERENCES ITD_MT_DISTRIBUTOR_LEAF(ID_DISTRIBUTOR_LEAF);

COMMENT ON TABLE ITD_MT_PRODUCT_PURCHASE IS 'Products per Purchase';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.ID_PRODUCT_PURCHASE IS 'ID of the product per purchase. Sequence: ITD_SEQ_PRODUCT_PURCHASE';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.ID_PURCHASE IS 'ID of the purchase. Foreign key towards the table ITD_MT_PURCHASE';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.ID_PRODUCT IS 'ID of the product. Foreign key towards the table ITD_MT_PRODUCT';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.ID_MANUFACTURER IS 'ID of the manufacturer. Foreign key towards the table ITD_MT_MANUFACTURER';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.ID_CATEGORY IS 'ID of the category. Foreign key towards the table ITD_MT_CATEGORY';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.ID_UNIT_TYPE IS 'ID of the unit type. Foreign key towards the table ITD_MT_UNIT_TYPE';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.ID_PACK_SIZE IS 'ID of the pack size. Foreign key towards the table ITD_MT_PACK_SIZE';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.ID_DISTRIBUTOR_ROOT IS 'ID of the distributor root. Foreign key towards the table ITD_MT_DISTRIBUTOR_ROOT';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.ID_DISTRIBUTOR_LEAF IS 'ID of the distributor leaf. Foreign key towards the table ITD_MT_DISTRIBUTOR_LEAF';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.AM_QUANTITY IS 'Quantity of the product';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.AM_PRICE IS 'Price of the product';
COMMENT ON COLUMN ITD_MT_PRODUCT_PURCHASE.AM_TOTAL IS 'Total of the product';


--Sequences
CREATE SEQUENCE ITD_SEQ_CITY INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_CUSTOMER_ROOT INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_CUSTOMER_LEAF INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_DISTRIBUTOR_ROOT INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_DISTRIBUTOR_LEAF INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_MANUFACTURER INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_CATEGORY INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_UNIT_TYPE INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_PACK_SIZE INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_PRODUCT INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_PURCHASE INCREMENT BY 1 START WITH 1 NOCACHE;
CREATE SEQUENCE ITD_SEQ_PRODUCT_PURCHASE INCREMENT BY 1 START WITH 1 NOCACHE;
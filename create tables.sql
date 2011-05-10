use WesternStyle;

CREATE TABLE customer
(
	id integer,
	name NvarChar(512),
	address NvarChar(512),
	zipCode integer,
	city NvarChar(512),
	phoneNo NvarChar(512),
	primary key(id)
);

CREATE TABLE invoice
(
	id integer,
	invoiceNo integer,
	paymentDate datetime,
	amount integer,
	primary key(id)
);

CREATE TABLE supplier
(
	id integer,
	name NvarChar(512),
	address NvarChar(512),
	country NvarChar(512),
	phoneNo NvarChar(512),
	email NvarChar(512),
	primary key(id)
);

CREATE TABLE salesOrder
(
	id integer,
	date datetime,
	amount integer,
	deliveryStatus NvarChar(512),
	deliveryDate datetime,
	customerId integer,
	invoiceId integer,
	primary key(id),
	foreign key(customerId) references customer(id),
	foreign key(invoiceId) references invoice(id)
);

CREATE TABLE product
(
	id integer,
	name NvarChar(512),
	purchasePrice float,
	salesPrice float,
	rentPrice float,
	countryOfOrigin NvarChar(512),
	minStock integer,
	supplierId integer,
	primary key(id),
	foreign key(supplierId) references supplier(id)
);

CREATE TABLE clothing
(
	id integer,
	productId integer,
	size integer,
	colour NvarChar(512),
	primary key(id),
	foreign key(productId) references product(id)
);

CREATE TABLE equipment
(
	id integer,
	productId integer,
	type NvarChar(512),
	description NvarChar(512),
	primary key(id),
	foreign key(productId) references product(id)
);

CREATE TABLE gunReplica
(
	id integer,
	productId integer,
	fabric NvarChar(512),
	calibre NvarChar(512),
	primary key(id),
	foreign key(productId) references product(id)
);

CREATE TABLE purchase
(
	id integer,
	salesOrderId integer,
	productId integer,
	primary key(id),
	foreign key(salesOrderId) references salesOrder(id),
	foreign key(productId) references product(id)
);

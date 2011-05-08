insert into customer (id,name,address,phoneNo,city,zipCode)
values(1, 'Joe Doe', 'Vesterbro 13', 
'+61763217', 'Aalborg', 9000);

insert into customer (id,name,address,phoneNo,city,zipCode)
values(2, 'Charlie Chaplin', 'Wallstreet 2', 
'+625365', 'New York', 5000);

insert into customer (id,name,address,phoneNo,city,zipCode)
values(3, 'Micheal Jordon', 'Marine 12', '+12313', 
'New York', 5500);

insert into invoice (id, invoiceNo, paymentDate, amount)
values(1, 123, CAST('20090303 00:00:00' AS datetime), 20);

insert into salesOrder(id, date, amount, deliveryStatus, deliveryDate, 
customerId, invoiceId) values(1, 
CAST('20091012 00:00:00' AS datetime), 321, 'delivered', 
CAST('20091121 00:00:00' AS datetime), 1, 1);

insert into product(id, name, purchasePrice, salesPrice, rentPrice, 
countryOfOrigin, minStock, supplierId) values(1, 'cowboy hat', 
100, 150, 120, 'Canada', 1, 1);

insert into product(id, name, purchasePrice, salesPrice, rentPrice, 
countryOfOrigin, minStock, supplierId) values(2, 
'Flag', 30, 50, 20, 'USA', 1, 1);

insert into product(id, name, purchasePrice, salesPrice, rentPrice, 
countryOfOrigin, minStock, supplierId) values(3, 
'rifle', 210, 250, 150, 'Denmark', 1, 1);

insert into clothing(id, productId, size, colour) 
values(1, 1, 12, 'red');

insert into equipment(id, productId, type, description) 
values(1, 2, 'flag', 'denmark\'s flag');

insert into gunReplica(id, productId, fabric, calibre) 
values(1, 3, 'wood', '9');

insert into supplier(id, name, address, country, phoneNo, email) 
values(1, 'cowboy heaven', 'vesterbro 12', 
'Denmark', '+7263476', 'cowboy.heaven@cowboy.dk');


SELECT COUNT(*) FROM order_detail o WHERE EXISTS (SELECT * FROM product p where p.name="ao den" and o.product_id =p.id);

SELECT COUNT(*) FROM product o where name = 'ao den';

DELETE FROM order_detail;


SELECT * FROM product WHERE name like "%ao%";


INSERT INTO product (name, category, origin, price, size, count) VALUES ('ao do',1,'USA',6000000.00,'M',4);

DELETE FROM product WHERE id = 4;

INSERT INTO product (name, category, origin, price, size, count) VALUES ("ao do",3,"VN",2000,"ML",5);
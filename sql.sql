SELECT COUNT(*) FROM order_detail o WHERE EXISTS (SELECT * FROM product p where p.name="ao den" and o.product_id =p.id);

SELECT COUNT(*) FROM product o where name = 'ao den';

DELETE FROM order_detail;

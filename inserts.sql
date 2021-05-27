insert into unitat values(1,'grams');
insert into unitat values(2,'kilograms');
insert into unitat values(3,'litres');



insert into Categoria values (1,'Primer',conv('33CC33',16,10));
insert into Categoria values (2,'Segon',conv('CC0099',16,10));
insert into Categoria values (3,'Postre',conv('44AA66',16,10));


insert into Plat values(1,'Amanida','Amanida d''enciam',3.5,null,true,1);
insert into Plat values(2,'Paella','Paella valenciana tradicional',9.5,null,true,2);
insert into Plat values(3,'Natilles','Natilles Danet',1,null, true,3);


insert into ingredient values(1,'Enciam');
insert into ingredient values(2,'Arròs');
insert into ingredient values(3,'Llet');


insert into Linea_Escandall values (1,1,200,1,1);
insert into Linea_Escandall values (2,1,100,1,2);
insert into Linea_Escandall values (3,1,1,3,3);


insert into Cambrer values(1,'Pepe','Perez','Gutierrez','pepe123','pepe123');
insert into Cambrer values(2,'Maria','Garcia','Fernandez','maria321','maria321');
insert into Cambrer values(3,'Antonio','Gonzalez','Lopez','antonio','antonio');


insert into taula (numero) values (1);
insert into taula (numero) values (2);
insert into taula (numero) values (3);

insert into comanda values (1,'2021-05-18 20:45:16',1,1);
insert into comanda values (2,'2021-05-18 20:50:29',2,2);
insert into comanda values (3,'2021-05-18 20:51:02',3,3);

UPDATE taula set comanda = 1 where numero = 1;
UPDATE taula set comanda = 2 where numero = 2;
UPDATE taula set comanda = 3 where numero = 3;

insert into estat_linia values (1);
insert into estat_linia values (2);

insert into linea_comanda values(1,1,2,1,true);
insert into linea_comanda values(1,2,5,2,false);
insert into linea_comanda values(2,3,4,3,false);
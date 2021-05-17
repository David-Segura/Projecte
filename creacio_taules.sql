create table unitat(
	number(7) codi,
	varchar2(40) nom
);

create table categoria(
	number (7) codi,
	varchar2 (40) nom,
	number(8)color
);
create table plat(
	number(7) codi,
	varchar2(40) nom,
	varchar2(100) descripcioMD,
	number(3,2) preu,
	blob foto,
	boolean disponible,
	number(7) categoria FOREIGN KEY REFERENCES Categoria(codi)
	
);
create table Linea_Escandall(
	number (7) plat foreign key references Plat(codi),
	number (7) num,
	number (3) quantitat,
	number (7) unitat foreign key references Unitat (codi),
	number (7) ingredient foreign key references Ingredient (codi),
);
create table Ingredient(
	number (7) codi,
	varchar2 (40) nom
);
create table Cambrer(
	number (7) codi,
	varchar2 (40) nom,
	varchar2 (40) cognom1,
	varchar2 (40) cognom2,
	varchar2 (40) user,
	varchar2 (40) password,
);
create table comanda(
	number (7) codi,
	date data,
	number(3) taula,
	number (7) cambrer foreign key references Cambrer (codi)
);
create table Linea_Comanda(
	number (3) num,
	number (3) quantitat,
	number (7) item foreign key references Plat(codi),
	number (1) estat foreign key references Estat_Linia(estat)
);
create table Taula(
	number (3) numero
);
create table Estat_Linia(
	number (1) estat
);

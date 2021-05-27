create table unitat(
	 codi integer (7),
	 nom varchar(40),
     PRIMARY KEY (codi)
);

create table categoria(
	codi integer (7),
	nom varchar (40),
	color integer (8),
    PRIMARY KEY (codi)
);
create table plat(
	codi integer(7),
	nom varchar(40),
	descripcioMD varchar(100),
	preu float,
	foto blob,
	disponible boolean,
	categoria integer(7),
    
    PRIMARY KEY (codi),
    CONSTRAINT FK_Plat_Categoria FOREIGN KEY (categoria)
    REFERENCES Categoria(codi)
    
    
    
	
);
create table Ingredient(
	codi integer (7),
	nom varchar (40),
    PRIMARY KEY (codi)
);
create table Linea_Escandall(
	plat integer (7),
	num integer (7),
	quantitat integer (3),
	unitat integer (7),
	ingredient integer (7),
    PRIMARY KEY (plat,num),
    CONSTRAINT FK_Escandall_Plat FOREIGN KEY (plat)
    REFERENCES Plat(codi),
    CONSTRAINT FK_Escandall_Unitat FOREIGN KEY (unitat)
    REFERENCES unitat(codi),
    CONSTRAINT FK_Escandall_Ingredient FOREIGN KEY (ingredient)
    REFERENCES Ingredient(codi)
);
create table Cambrer(
	codi integer (7),
	nom varchar (40),
	cognom1 varchar (40),
	cognom2 varchar (40),
	user varchar (40),
	password varchar (40),
    PRIMARY KEY (codi)
);
create table Taula(
	numero integer (3),
	comanda integer (7)
    PRIMARY KEY (numero)	
	
);
create table comanda(
	codi integer (7),
	data datetime,
	taula integer(3),
	cambrer integer (7),
    PRIMARY KEY (codi),
    CONSTRAINT FK_Comanda_Taula FOREIGN KEY (taula)
    REFERENCES Taula(numero),
    CONSTRAINT FK_Comanda_Cambrer FOREIGN KEY (cambrer)
    REFERENCES Cambrer(codi)
);

alter table taula add CONSTRAINT FK_Taula_Comanda FOREIGN KEY (comanda) REFERENCES Comanda(codi);

create table Estat_Linia(
	estat integer (1),
    PRIMARY KEY (estat)
);
create table Linea_Comanda(
    comanda integer (7),
	num integer (3),
	quantitat integer (3),
	item integer (7),
	acabat boolean,
    PRIMARY KEY (comanda,num),
    CONSTRAINT FK_Lin_Com_Plat FOREIGN KEY (item)
    REFERENCES Plat(codi),
	CONSTRAINT FK_Lin_Com_Comanda FOREIGN KEY (comanda)
    REFERENCES Comanda(codi)
);
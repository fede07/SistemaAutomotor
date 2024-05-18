SELECT * FROM titular

CREATE TABLE titular (
    dni INT PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    direccion VARCHAR(50),
    email VARCHAR(50),
    telefono VARCHAR(50)
);

-- Insertar 10 datos de titulares

INSERT INTO titular (dni, nombre, apellido, direccion, email, telefono) VALUES (36806932, 'Juan', 'Perez', '3 de Febrero 691', 'perezjuan@gmail.com', '4652-3147');
INSERT INTO titular (dni, nombre, apellido, direccion, email, telefono) VALUES 
(12345678, 'Maria', 'Lopez', 'Av. Rivadavia 123', 'marialopez@gmail.com', '1234-5678'),
(23456789, 'Pedro', 'Gomez', 'Calle 123', 'pedrogomez@gmail.com', '9876-5432'),
(34567890, 'Laura', 'Rodriguez', 'Av. Corrientes 456', 'laurarodriguez@gmail.com', '4567-8901'),
(45678901, 'Carlos', 'Fernandez', 'Calle 456', 'carlosfernandez@gmail.com', '8901-2345'),
(56789012, 'Ana', 'Martinez', 'Av. Santa Fe 789', 'anamartinez@gmail.com', '2345-6789'),
(67890123, 'Luis', 'Sanchez', 'Calle 789', 'luissanchez@gmail.com', '6789-0123'),
(78901234, 'Lucia', 'Gonzalez', 'Av. Cordoba 012', 'luciagonzalez@gmail.com', '0123-4567'),
(89012345, 'Diego', 'Lopez', 'Calle 012', 'diegolopez@gmail.com', '4567-8901'),
(90123456, 'Carolina', 'Perez', 'Av. Mayo 345', 'carolinaperez@gmail.com', '8901-2345'),
(01234567, 'Martin', 'Gomez', 'Calle 345', 'martingomez@gmail.com', '2345-6789');

CREATE TABLE municipio (
    id INT PRIMARY KEY,
    nombre VARCHAR(50)
);

CREATE TABLE automotor (
    dominio VARCHAR(6) PRIMARY KEY,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    anio_fabricacion INT,
    titular INT,
    municipio INT,
    FOREIGN KEY (titular_dni) REFERENCES titular(dni),
    FOREIGN KEY (municipio_id) REFERENCES municipio(id)
);

SELECT * FROM automotor
-- Insertar 10 datos de automotores

INSERT INTO automotor (dominio, marca, modelo, anio_fabricacion, titular, municipio) VALUES ('ABC123', 'Ford', 'Fiesta', 2020, 36806932, 9);
INSERT INTO automotor (dominio, marca, modelo, anio_fabricacion, titular, municipio) VALUES 
('DEF456', 'Chevrolet', 'Corsa', 2019, 12345678, 1),
('GHI789', 'Fiat', 'Palio', 2018, 23456789, 2),
('JKL012', 'Volkswagen', 'Gol', 2017, 34567890, 3),
('MNO345', 'Renault', 'Kwid', 2016, 45678901, 4),
('PQR678', 'Peugeot', '208', 2015, 56789012, 5),
('STU901', 'Toyota', 'Etios', 2014, 67890123, 6),
('VWX234', 'Nissan', 'March', 2013, 78901234, 7),
('YZA567', 'Citroen', 'C3', 2012, 89012345, 8),
('BCD890', 'Honda', 'Fit', 2011, 90123456, 9);

SELECT * FROM boleta    

CREATE TABLE boleta (
    id_boleta INT PRIMARY KEY,
    cuota INT,
    estado VARCHAR(50),
    fecha_pago DATE,
    fecha_vencimiento DATE,
    importe DECIMAL(10,2),
    dominio VARCHAR(6),
    FOREIGN KEY (dominio) REFERENCES automotor(dominio)
);

-- Insertar 10 datos de boletas
INSERT INTO boleta (cuota, estado, fecha_pago, fecha_vencimiento, importe, dominio) VALUES (1, 'Pendiente', '2021-01-01', '2021-01-31', 1000.00, 'ABC123');
INSERT INTO boleta (cuota, estado, fecha_pago, fecha_vencimiento, importe, dominio) VALUES 
(2, 'Pendiente', '2021-02-01', '2021-02-28', 1000.00, 'DEF456'),
(3, 'Pendiente', '2021-03-01', '2021-03-31', 1000.00, 'GHI789'),
(4, 'Pendiente', '2021-04-01', '2021-04-30', 1000.00, 'JKL012'),
(5, 'Pendiente', '2021-05-01', '2021-05-31', 1000.00, 'MNO345'),
(6, 'Pendiente', '2021-06-01', '2021-06-30', 1000.00, 'PQR678'),
(7, 'Pendiente', '2021-07-01', '2021-07-31', 1000.00, 'STU901'),
(8, 'Pendiente', '2021-08-01', '2021-08-31', 1000.00, 'VWX234'),
(9, 'Pendiente', '2021-09-01', '2021-09-30', 1000.00, 'YZA567'),
(10, 'Pendiente', '2021-10-01', '2021-10-31', 1000.00, 'BCD890');

-- generar 10 boletas para el automotor con dominio 'ABC123', una cuota por mes, los primeros 3 meses pagados, con vencimiento a fin de mes y un importe de $3000.00

INSERT INTO boleta (cuota, estado, fecha_pago, fecha_vencimiento, importe, dominio) VALUES 
(1, 'Pagado', '2021-01-01', '2021-01-31', 1000.00, 'ABC123'),
(2, 'Pagado', '2021-02-01', '2021-02-28', 1000.00, 'ABC123'),
(3, 'Pagado', '2021-03-01', '2021-03-31', 1000.00, 'ABC123'),
(4, 'Pendiente', '2021-04-01', '2021-04-30', 1000.00, 'ABC123'),
(5, 'Pendiente', '2021-05-01', '2021-05-31', 1000.00, 'ABC123'),
(6, 'Pendiente', '2021-06-01', '2021-06-30', 1000.00, 'ABC123'),
(7, 'Pendiente', '2021-07-01', '2021-07-31', 1000.00, 'ABC123'),
(8, 'Pendiente', '2021-08-01', '2021-08-31', 1000.00, 'ABC123'),
(9, 'Pendiente', '2021-09-01', '2021-09-30', 1000.00, 'ABC123'),
(10, 'Pendiente', '2021-10-01', '2021-10-31', 1000.00, 'ABC123');

-- generar 10 boletas para el automotor con dominio 'DEF456', una cuota por mes, los primeros 3 meses pagados, con vencimiento a fin de mes y un importe de $3200.00
INSERT INTO boleta (cuota, estado, fecha_pago, fecha_vencimiento, importe, dominio) VALUES 
(1, 'Pagado', '2021-01-01', '2021-01-31', 1000.00, 'DEF456'),
(2, 'Pagado', '2021-02-01', '2021-02-28', 1000.00, 'DEF456'),
(3, 'Pagado', '2021-03-01', '2021-03-31', 1000.00, 'DEF456'),
(4, 'Pendiente', '2021-04-01', '2021-04-30', 1000.00, 'DEF456'),
(5, 'Pendiente', '2021-05-01', '2021-05-31', 1000.00, 'DEF456'),
(6, 'Pendiente', '2021-06-01', '2021-06-30', 1000.00, 'DEF456'),
(7, 'Pendiente', '2021-07-01', '2021-07-31', 1000.00, 'DEF456'),
(8, 'Pendiente', '2021-08-01', '2021-08-31', 1000.00, 'DEF456'),
(9, 'Pendiente', '2021-09-01', '2021-09-30', 1000.00, 'DEF456'),
(10, 'Pendiente', '2021-10-01', '2021-10-31', 1000.00, 'DEF456');


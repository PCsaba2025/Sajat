CREATE TABLE Korok (
    Sugar number (4) PRIMARY KEY,
    Kerulet number,
    Terulet number
    );
    
    Describe Korok;
    
SELECT 1 Sugar, 2*1*3.141592654 kerület from dual;

CREATE OR REPLACE Function PI RETURN number AS
BEGIN
RETURN 3.141592654;
END;

SELECT 1 Sugar, 2*1*PI() Kerulet from dual;

DECLARE
    Sugar number:=1;
    Kerulet number;
BEGIN  
    Kerulet := 2*Sugar*PI();
    DBMS_OUTPUT.PUT_LINE('Sugar: '||Sugar||' Kerulet: '
    ||Kerulet);
END;


Körnek a területe

DECLARE
    Sugar number:=1;
    Terulet number;
BEGIN  
    Terulet := POWER (Sugar, 2) * PI();
    DBMS_OUTPUT.PUT_LINE('Sugar: '||Sugar||' Terulet: '
    ||Terulet); 
END;


Több kör kerulet ciklussal

DECLARE
    Kerulet number;
    x number:=1;
    y number:=5;
BEGIN
    FOR i IN x..y LOOP
        Kerulet:= 2*i*PI();
        DBMS_OUTPUT.PUT_LINE('Sugar: '||i||' Kerulet: '
    ||Kerulet); 
    END LOOP;
END;

TAROLT ELJARASSAL

CREATE OR REPLACE Procedure Kor (x in number, y in number) IS
    Kerulet number;
BEGIN
    FOR i IN x..y LOOP
        Kerulet:= 2*i*PI();
        DBMS_OUTPUT.PUT_LINE('Sugar: '||i||' Kerulet: '
    ||Kerulet); 
    END LOOP;
END;

tarolt eljaras hivasa

BEGIN
    Kor(1, 5);
END;

KOR KERULETE TERULETE KIIRASSAL

CREATE OR REPLACE Procedure Circle (x in number, y in number) IS
    Circumference number;
    Area number;
BEGIN
    FOR i IN x..y LOOP
        Circumference:= 2*i*PI();
        Area:= POWER(i, 2)*PI();
        DBMS_OUTPUT.PUT_LINE('Radius: '||i||' Circumference: '
    ||Circumference|| 'Area: ' ||Area); 
    END LOOP;
END;

BEGIN



MASIK 

CREATE TABLE Korok (
    Sugar number (4) PRIMARY KEY,
    Kerulet number,
    Terulet number
    );
    
    Describe Korok;
    
SELECT 1 Sugar, 2*1*3.141592654 kerület from dual;

CREATE OR REPLACE Function PI RETURN number AS
BEGIN
RETURN 3.141592654;
END;

SELECT 1 Sugar, 2*1*PI() Kerulet from dual;

DECLARE
    Sugar number:=1;
    Kerulet number;
BEGIN  
    Kerulet := 2*Sugar*PI();
    DBMS_OUTPUT.PUT_LINE('Sugar: '||Sugar||' Kerulet: '
    ||Kerulet);
END;


--Körnek a területe

DECLARE
    Sugar number:=1;
    Terulet number;
BEGIN  
    Terulet := POWER (Sugar, 2) * PI();
    DBMS_OUTPUT.PUT_LINE('Sugar: '||Sugar||' Terulet: '
    ||Terulet); 
END;


--Több kör kerulet ciklussal

DECLARE
    Kerulet number;
    x number:=1;
    y number:=5;
BEGIN
    FOR i IN x..y LOOP
        Kerulet:= 2*i*PI();
        DBMS_OUTPUT.PUT_LINE('Sugar: '||i||' Kerulet: '
    ||Kerulet); 
    END LOOP;
END;

--TAROLT ELJARASSAL

CREATE OR REPLACE Procedure Kor (x in number, y in number) IS
    Kerulet number;
BEGIN
    FOR i IN x..y LOOP
        Kerulet:= 2*i*PI();
        DBMS_OUTPUT.PUT_LINE('Sugar: '||i||' Kerulet: '
    ||Kerulet); 
    END LOOP;
END;

--tarolt eljaras hivasa

BEGIN
    Kor(1, 5);
END;

--KOR KERULETE TERULETE KIIRASSAL

CREATE OR REPLACE Procedure Circle (x in number, y in number) IS
    Circumference number;
    Area number;
BEGIN
    FOR i IN x..y LOOP
        Circumference:= 2*i*PI();
        Area:= POWER(i, 2)*PI();
        DBMS_OUTPUT.PUT_LINE('Radius: '||i||' Circumference: '
    ||Circumference|| 'Area: ' ||Area); 
    END LOOP;
END;

--A kör adatainak a táblába írása:

CREATE OR REPLACE Procedure Circle (x in number, y in number) IS
    Kerulet number;
    Terulet number;
BEGIN
    FOR i IN x..y LOOP
        Kerulet:= 2*1*PI();
        Terulet:= POWER(i, 2)*PI();
        INSERT INTO Korok1 VALUES(i, Kerulet, Terulet);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('A kör táblába bekerültek az adatok');
END;

BEGIN
    Circle(1, 5);
END;

--SQL parancs - a tábla lekérdezése
SELECT * FROM Korok1;

CREATE TABLE Korok1 (
    Sugar NUMBER,
    Kerulet NUMBER,
    Terulet NUMBER
    );

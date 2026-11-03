CREATE TABLE auto2 (
    ID number(4) PRIMARY KEY,
    Rendszam varchar2(6),
    Tipus varchar2(30),
    Szin varchar2(20),
    Kor number(3),
    Ar number(10),
    Muszaki_erv date
);

desc Auto2;

--adatok beszurasa

BEGIN
INSERT INTO auto2 VALUES(1, 'ABC123','Opel Corsa','Piros',6,3200000,TO_DATE('05.08.2024','DD-MM-YYYY'));
INSERT INTO auto2 VALUES(2, 'QWJ612','Suzuki Alto','Kék',15,5000000,TO_DATE('02.018.2024','DD-MM-YYYY'));
INSERT INTO auto2 VALUES(3, 'PHD229','Skoda Scala','Fehér',1,6800000,TO_DATE('03.12.2024','DD-MM-YYYY'));
INSERT INTO auto2 VALUES(4, 'PET220','Opel Astra','Fekete',3,4400000,TO_DATE('08.05.2024','DD-MM-YYYY'));
INSERT INTO auto2 VALUES(5, 'RWE611','Ford Focus','Sárga',4,5300000,TO_DATE('05.12.2024','DD-MM-YYYY'));
INSERT INTO auto2 VALUES(6, 'MNS418','Skoda Karoq','Kék',2,7000000,TO_DATE('25.11.2024','DD-MM-YYYY'));
INSERT INTO auto2 VALUES(7, 'MUX312','Suzuki Swift','Piros',8,1600000,TO_DATE('05.09.2024','DD-MM-YYYY'));
END;

select * from auto2;

--1 implicit kurzor

Create or replace procedure KurzMintPld (szinbe varchar2) as pc int;
BEGIN
        UPDATE auto2 set Ar = Ar + 400000 where Szin = szinbe;
    IF sql%notfound Then
        dbms_output.put_line('Nincs '||szinbe||' színű auto!');
    ELSIF sql%found then
        pc := sql%rowcount;
        dbms_output.put_line(pc||' db '||szinbe||' auto ára módosítva!');
    end if;
end;

select * from auto2;

SELECT Rendszam, Szin, Ar from auto2 where Trim(lower(Szin)) = 'Piros'; --ez nem biztos hogy kell (Van-e piros auto?)

Begin
kurzmintpld('Piros');
End;

--2 explicit kurzor - loop ciklus

create or replace Procedure KurzMintPld1 (szinbe varchar2) as 
 CURSOR cur is select Rendszam, Ar from Auto2 where Szin = szinbe for update of Ar;
    cv cur%rowtype;
    pc int;
Begin
 open cur;
 loop
 fetch cur into cv;
    exit when cur%notfound;
    update Auto2 set Ar = cv.Ar - 200000 where current of cur;
 end loop;
    pc := cur%rowcount;
    dbms_output.put_line(pc||' db '||szinbe||' auto ára módosítva!');
close cur;
end;

begin
kurzmintpld1('Piros');
end;

--3 for ciklus

create or replace procedure KurzMintPld2 (szinbe varchar2) as
 CURSOR cur is select Rendszam, Ar from Auto2 where szin= szinbe for update of Ar;
  pc int;
begin
 for cv in cur loop
 update Auto2 set Ar = cv.Ar - 200000 where current of cur;
 pc:= cur%rowcount;
 end loop;
 if pc != 0 then
 dbms_output.put_line(pc||' db '||szinbe||' auto ára módosítva!');
 else dbms_output.put_line('nincs '||szinbe||' szinű auto!');
 end if;
end;

begin
kurzmintpld2('Sárga');
end;

--4 

create or replace procedure DataOut (tipusbe varchar2) is
 CURSOR cur is select Rendszam, Tipus, Ar from auto2 where tipus like tipusbe||'%';
 c_var cur%rowtype;
begin
 open cur;
 loop
 fetch cur into c_var;
 exit when cur%notfound;
 dbms_output.put_line('Rendszám: '||c_var.rendszam||' típus: '||c_var.tipus||' ár: '||c_var.ar);
 end loop;
 close cur;
end;

begin
DataOut('Opel Corsa');
end;
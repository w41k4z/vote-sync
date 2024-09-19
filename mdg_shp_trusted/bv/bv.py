import oracledb
import csv

connection = oracledb.connect(
    user="election",
    password="election",
    dsn="localhost/XEPDB1"
)
cursor = connection.cursor()

foks = {}
with open('../liste-bv.csv', 'r') as bvfile:
    file_content = csv.reader(bvfile, delimiter=';')
    i = 0
    for row in file_content:
        if i == 0:
            i+=1
            continue
        bv_code = row[6]
        fokontany = row[4].upper()
        if fokontany in ['ANDAVAMAMBA AMBILANIBE', 'AMPANGABE ANJANAKINIFOLO', 'AMBODIVOANJO AMBOHIJATOVO FARA', 'IVANDRY']: 
            bv = row[7].upper()
            code = bv_code
            script = "INSERT INTO imported_bv(code, nom) values(:code, :nom)"
            cursor.execute(script, {
                "code": code,
                "nom": bv
            })
    cursor.execute("commit")
    print('All ok')
    cursor.close()
    connection.close()
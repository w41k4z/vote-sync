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
    cvs = []
    for row in file_content:
        if i == 0:
            i+=1
            continue
        bv_code = row[6]
        fokontany = row[4].upper()
        if fokontany in ['ANDAVAMAMBA AMBILANIBE', 'AMPANGABE ANJANAKINIFOLO', 'AMBODIVOANJO AMBOHIJATOVO FARA', 'IVANDRY']: 
            region = row[1].upper()
            district = row[2].upper()
            commune = row[3].upper()
            cv = row[5].upper()
            if cv in cvs:
                continue
            cvs.append(cv)
            key = cv
            code = bv_code[0:10]
            script = "INSERT INTO imported_cv(code, region, district, commune, fokontany, nom) values(:code, :region, :district, :commune, :fokontany, :nom)"
            cursor.execute(script, {
                "code": code,
                "region": region,
                "district": district,
                "commune": commune,
                "fokontany": fokontany,
                "nom": cv
            })
    cursor.execute("commit")
    print('All ok')
    
    # EPP Anosipatrana: 47.498795538838856, -18.927096848540742
    # EPP Ilanivato: 47.50052955772094, -18.922487660553262
    # LTP Alarobia: 47.524082294658704, -18.87386865070247
    # EPP Ivandry: 47.529019061577834, -18.87291175383014
    data = [
        {
            'code': '1110010301', # EPP Anosipatrana,
            'x': 47.498795538838856,
            'y': -18.927096848540742
        },
        {
            'code': '1110010401', # EPP Ilanivato,
            'x': 47.50052955772094,
            'y': -18.922487660553262
        },
        {
            'code': '1111012001', # LTP Alarobia,
            'x': 47.524082294658704,
            'y': -18.87386865070247
        },
        {
            'code': '1111010601', # EPP Ivandry,
            'x': 47.529019061577834,
            'y': -18.87291175383014
        }
    ]
    for each in data:
        type_obj = connection.gettype("MDSYS.SDO_GEOMETRY")
        point_type_obj = connection.gettype("MDSYS.SDO_POINT_TYPE")
        obj = type_obj.newobject()
        obj.SDO_GTYPE = 2001
        obj.SDO_SRID = 4326
        obj.SDO_POINT = point_type_obj.newobject()
        obj.SDO_POINT.X = each['x']
        obj.SDO_POINT.Y = each['y']
        obj.SDO_POINT.Z = None
        script = "UPDATE cv SET localisation = :obj WHERE code = :code"
        cursor.execute(script, {
            "obj": obj,
            "code": each['code']
        })
    cursor.execute("commit")
    cursor.close()
    connection.close()
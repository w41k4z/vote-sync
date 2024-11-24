import oracledb
import csv

connection = oracledb.connect(
    user="election",
    password="election",
    dsn="localhost/XEPDB1"
)
cursor = connection.cursor()


    
# EPP Volotara: 47.52559855445333, -18.981345587587416
data = [
    {
        'code': '1105110601', # EPP Volotara,
        'x': 47.52559855445333,
        'y': -18.981345587587416
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
    script = "INSERT INTO cv(code, id_fokontany, nom, localisation, etat) VALUES(:code, :fokontanyId, :cv, :localisation, :status)"
    cursor.execute(script, {
        "code": "1105110601",
        "fokontanyId": 21,
        "cv": "EPP VOLOTARA",
        "localisation": obj,
        "status": 0
    })
cursor.execute("commit")
cursor.close()
connection.close()
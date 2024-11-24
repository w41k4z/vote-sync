import oracledb
import json

connection = oracledb.connect(
    user="election",
    password="election",
    dsn="localhost/XEPDB1"
)
cursor = connection.cursor()

# Cleaned Region (with spatial data)
import geojson

with open('Fokontany.geojson', 'r') as f:
    geojson_data = geojson.load(f)
    for each in geojson_data["features"]:
        p_code = each["properties"]["P_CODE"]
        fokontany = each["properties"]["FOKONTANY"]
        if fokontany == 'Volotara Firaisana':
            str_geojson = json.dumps(each["geometry"])
            
            script = "INSERT INTO fokontany(code, id_commune, nom, geojson, etat) values(:code, :communeId, :fokontany, :geojson, :status)"
            cursor.execute(script, {
                "code": "11051106",
                "communeId": 22,
                "fokontany": "VOLOTARA",
                "geojson": str_geojson,
                "status": 0
            })
    cursor.execute("commit")
    print('All ok')
    cursor.close()
    connection.close()
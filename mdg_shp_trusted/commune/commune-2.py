import oracledb

connection = oracledb.connect(
    user="election",
    password="election",
    dsn="localhost/XEPDB1"
)

query = """
    select 
    i_c.*, 
    d.code as code_district,
    d.id as id_district
from imported_communes i_c
join imported_districts i_p 
    on i_p.p_code = i_c.dist_pcode
join districts d
    on i_p.code = d.code
"""

# Execute this query and store the fetched row into some kind of dict
cursor = connection.cursor()
cursor.execute(query)
rows = cursor.fetchall()
codes = {}
for row in rows:
    code = row[6]
    if code not in codes:
        codes[code] = 1
    str_comp = '0' + str(codes[code]) if codes[code] < 10 else str(codes[code])
    commune_code = code + str_comp
    new_query = "insert into communes(code, id_district, nom, geojson) values(:code, :id_district, :nom, :geojson)"
    cursor.execute(new_query, {
        "code": commune_code,
        "id_district": row[7],
        "nom": row[4],
        "geojson": row[5]
    })
    codes[code] += 1
cursor.execute("commit")
cursor.close()
connection.close()
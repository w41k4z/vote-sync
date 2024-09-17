import oracledb

def generate_sdo_elem_info_array(num_polygons):
    array = []
    
    # Start of multipolygon
    array.append(1)
    
    # Each polygon is treated as an independent entity
    for _ in range(num_polygons):
        array.append(1003)
        array.append(1)
    return array

import csv

regs = {}
# Raw region
with open('result.csv', 'r') as csvfile:
    file_content = csv.reader(csvfile, delimiter=';')
    i = 0
    regions = []
    for row in file_content:
        if i == 0:
            i+=1
            continue
        the_region = row[0]
        if the_region == 'VATOVAVY':
            continue
        if the_region == 'FITOVINANY':
                the_region = 'VATOVAVY FITOVINANY'
        if the_region == 'ATSIMO-ATSINANAN':
                the_region = 'ATSIMO ATSINANANA'
        if the_region == 'ALAOTRA-MANGOR':
                the_region = 'ALAOTRA MANGORO'
        if the_region == 'ATSIMO-ANDREFAN':
                the_region = 'ATSIMO ANDREFANA'
        if the_region not in regions:
            regions.append(the_region)
            regs[the_region] = {"id": row[5].replace(' ', '')[0:2]}
            
# Raw region + column P_CODE and R_CODE    
with open('Regions.csv', 'r') as csvfile:
    file_content = csv.reader(csvfile, delimiter=',')
    i = 0
    for row in file_content:
        if i == 0:
            i+=1
            continue
        regs[row[3].upper()]['PROV_ID'] = row[0]
        regs[row[3].upper()]['P_CODE'] = row[4]
        regs[row[3].upper()]['R_CODE'] = row[5]

# Cleaned Region (with spatial data)
import geojson

with open('Regions.geojson', 'r') as f:
    geojson_data = geojson.load(f)
    connection = oracledb.connect(
        user="election",
        password="election",
        dsn="localhost/XEPDB1"
    )
    cursor = connection.cursor()
    with open('cleaned/region.sql', 'w') as w:
        for each in geojson_data["features"]:
            coordinates = each["geometry"]["coordinates"][0]
            sd_elem_info = ''
            if each["geometry"]["type"] == 'MultiPolygon':
                sd_elem_info = generate_sdo_elem_info_array(len(coordinates))
            else:
                sd_elem_info = [1, 1003, 1]
            arr_coordinates = []
            for coord in coordinates:
                if each["geometry"]["type"] == 'MultiPolygon':
                    for c in coord:
                        arr_coordinates.append(float(c[0]))
                        arr_coordinates.append(float(c[1]))
                else:
                    arr_coordinates.append(float(coord[0]))
                    arr_coordinates.append(float(coord[1]))
            id = regs[each["properties"]["REGION"].upper()]["id"]
            prov_id = regs[each["properties"]["REGION"].upper()]["PROV_ID"]
            p_code = regs[each["properties"]["REGION"].upper()]["P_CODE"]
            r_code = regs[each["properties"]["REGION"].upper()]["R_CODE"]
            region = each["properties"]["REGION"].upper()
            
            type_obj = connection.gettype("MDSYS.SDO_GEOMETRY")
            element_info_type_obj = connection.gettype("MDSYS.SDO_ELEM_INFO_ARRAY")
            ordinate_type_obj = connection.gettype("MDSYS.SDO_ORDINATE_ARRAY")
            obj = type_obj.newobject()
            obj.SDO_GTYPE = 2007
            obj.SDO_SRID = 8307
            obj.SDO_ELEM_INFO = element_info_type_obj.newobject()
            obj.SDO_ELEM_INFO.extend(sd_elem_info)
            obj.SDO_ORDINATES = ordinate_type_obj.newobject()
            obj.SDO_ORDINATES.extend(arr_coordinates)
            
            script = "INSERT INTO regions(id, id_province, p_code, r_code, nom, geom) values(:id, :prov_id, :p_code, :r_code, :region, :obj)"
            cursor.execute(script, {
                "id": id,
                "prov_id": prov_id,
                "p_code": p_code,
                "r_code": r_code,
                "region": region,
                "obj": obj
            })
            print('Tafiditra \n')
        cursor.execute("commit")
        cursor.close()
        connection.close()
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
    with open('cleaned/region.sql', 'w') as w:
        for each in geojson_data["features"]:
            coordinates = each["geometry"]["coordinates"][0]
            str_coordinates = ''
            for coord in coordinates:
                str_coordinates += str(coord[0]) + ',' + str(coord[1]) + ','
            str_coordinates = str_coordinates[:-1]
            script = 'insert into regions values (' + regs[each["properties"]["REGION"].upper()]["id"] + ', \'' + each["properties"]["REGION"].upper() + '\', ' + regs[each["properties"]["REGION"].upper()]["PROV_ID"] + ', \'' + regs[each["properties"]["REGION"].upper()]["P_CODE"] + '\', \'' + regs[each["properties"]["REGION"].upper()]["R_CODE"] + '\', SDO_GEOMETRY(2007,8307,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1, 7,1003,1),SDO_ORDINATE_ARRAY(' + str_coordinates + ')));'
            w.write(script + '\n')
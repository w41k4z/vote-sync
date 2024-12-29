# Project Setup Guide

## Environment Requirements

Ensure the following software versions are installed on your system:

- **Maven**: 3.x
- **Java**: SE 17.0.12
- **Oracle**: 21c Express Edition
- **Node.js**: 22.12.0 (with NPM 11.0.0)
- **Python**: 3.12.8

---

## Installation Steps

### Database Setup

1. **Connect as SYSDBA**:

   ```bash
   sqlplus sys as sysdba
   ```

2. **Switch the session container**:

   ```sql
   ALTER SESSION SET CONTAINER = XEPDB1;
   ```

3. **Create the 'election' user**:

   ```sql
   CREATE USER election IDENTIFIED BY election;
   ```

4. **Grant privileges to the user**:

   ```sql
   GRANT CONNECT, RESOURCE, UNLIMITED TABLESPACE, CREATE VIEW, CREATE PROCEDURE TO election;
   ```

5. **Reconnect as 'election'**:

   ```bash
   sqlplus election/election@localhost:1521/XEPDB1
   ```

6. **Execute the following SQL scripts**:

   - `system/table.spatial.sql`
   - `system/table.sql`
   - `external/table.sql`
   - `external/table.init.spatial.data.sql`
   - `system/domain.datawarehouse.sql`
   - `system/index.sql`
   - `system/trigger.sql`
   - `system/view.sql`
   - `external/view.sql`
   - `system/view.result.sql`
   - `system/view.stat.sql`
   - `external/function.sql`
   - `system/function.spatial.sql`
   - `external/procedure.init.spatial.data.sql`
   - `system/procedure.sql` (execute twice)
   - `functional.data.sql`

7. **Execute the following Python scripts**:
   - `region/region.py`
   - `district/district.py`
   - `municipalite/municipalite.py`
   - `commune/commune.py`
   - `fokontany/fokontany.py`
   - `cv/cv.py`
   - `bv/bv.py`

### Server Setup

Start the server using Maven:

```bash
mvn spring-boot:run
```

### Client Setup

1. Install Node.js dependencies:

   ```bash
   npm install
   ```

2. Run the Angular development server:
   ```bash
   ng serve
   ```

---

### Additional Notes

- Ensure the Oracle database is running before executing scripts.
- Use separate terminals for the server (`spring-boot`) and client (`ng serve`) to monitor logs effectively.
- Verify all dependencies are compatible with the versions listed above.

---

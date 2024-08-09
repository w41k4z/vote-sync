import tabula
import pandas as pd


def pdf_to_excel(pdf_file_path, excel_file_path):
    # Read PDF file
    tables = tabula.read_pdf(pdf_file_path, pages="all", multiple_tables=False)
    combined_df = pd.concat(tables, ignore_index=True)
    combined_df.to_excel(excel_file_path, index=False)
    # # Write each table to a separate sheet in the Excel file
    # with pd.ExcelWriter(excel_file_path) as writer:
    #     for i, table in enumerate(tables):
    #         table.to_excel(writer, sheet_name=f'Sheet{i+1}')


pdf_to_excel('LISTE-ET-EMPLKACEMENT-BV-ET-NOMBRES-DES-ELECTEURS-PAR-BV-DU-15-MAI-2024.pdf', 'result.xlsx')
# -*- coding: utf-8 -*-
#Author:Jacky Zhang
#Version:3.0
# Data: 2017-12-6

import os
import sys
import xlwt
import datetime
import logging
from selenium import webdriver

class iPay_main:
    def __init__(self):
        #Current Log Path:
        path = os.path.abspath('.')
        self.curr_dir = path+'\\Logs'
        self.data_center = os.getenv('TEST_DATA', path+'\\TestData')
        self.script_dir = path+'\\Library\\'

    def create_log_path(self, outputdir=None):
        """Create Case log Directory
        Usage: | Create Log Path | caseName | outputDir |
        """
        LOG_PATH = str(outputdir)
        log_dir = os.getenv('LOG_DIR')
        print('=' * 100)
        print(LOG_PATH)
        print('=' * 100)
        # Window code
        base = self.curr_dir
        os.system('rd /Q ' + '"' + os.path.join(base, 'current') + '"')
        os.system('mklink /D ' + '"' + os.path.join(base, 'current') + '"' + ' ' + '"' + LOG_PATH + '"')

    def _Read_From_File(self, filename):
        # Read data from the given file
        file = os.path.join(self.curr_dir, filename) # Default File Path
        global data
        data = " "
        try:
            f = open(file, 'r')
            # data = f.read().decode('utf-8')
            data = f.read()
            f.close()
        except Exception as e:
            print(str(e))
        return data

    def _Reorganize_Data(self, data):
        # Reorganize data for writing them to Excel file
        allList = []
        reorganized = data.split('|')
        for row in reorganized:
            allList.append(row.split('::'))
        return allList

    def _Set_Style(self, name, height, colour=1, bold=False, pattern_colour=None):
        # default font style

        # Cell set alignment
        alignment = xlwt.Alignment()
        alignment.horz = xlwt.Alignment.HORZ_LEFT
        alignment.vert = xlwt.Alignment.VERT_CENTER

        # Cell add borders
        borders = xlwt.Borders()
        borders.left = xlwt.Borders.THIN
        borders.right = xlwt.Borders.THIN
        borders.top = xlwt.Borders.THIN
        borders.bottom = xlwt.Borders.THIN
        borders.left_colour = 0x40
        borders.right_colour = 0x40
        borders.top_colour = 0x40
        borders.bottom_colour = 0x40

        # Cell set font style
        font = xlwt.Font()
        font.name = name
        font.height = height
        font.colour_index = colour
        font.bold = bold

        if pattern_colour != None:
            # Cell set background colour
            pattern = xlwt.Pattern()
            pattern.pattern = xlwt.Pattern.SOLID_PATTERN
            pattern.pattern_fore_colour = pattern_colour

        style = xlwt.XFStyle()
        style.alignment = alignment
        if pattern_colour != None:
            # Cell set background colour
            style.pattern = pattern
        style.borders = borders
        style.font = font
        return style

    def _Write_To_Excel_File(self, filename, *data):
        """
        Write the values to the Excel file.

        """
        # reload(sys)
        # sys.setdefaultencoding('cp936')

        try:
            xls_file = xlwt.Workbook(style_compression=2)
            sheet1 = xls_file.add_sheet('Test Report', cell_overwrite_ok=True)
            sheet2 = xls_file.add_sheet('Test Result', cell_overwrite_ok=True)

            # Write Test Result

            # Cell set width of column
            col_wid2 = sheet2.col(0)
            col_wid2.width = 256*50
            col_wid2 = sheet2.col(1)
            col_wid2.width = 256*10
            col_wid2 = sheet2.col(2)
            col_wid2.width = 256*100
            col_wid2 = sheet2.col(3)
            col_wid2.width = 256*20

            # Write report name & subtitle
            started_time = os.getenv('TASK_START_TIME')
            generated_time = datetime.datetime.now()
            report_status = ['Start Time', '%s' % started_time, 'End Time', '%s' % str(generated_time)]
            row_status = ['Case', 'Status', 'Error Message', 'Cause of Error']
            sheet2.write_merge(0, 3, 0, 0, 'Test Result', self._Set_Style('Arial', 210, 17, True))
            sheet2.write_merge(0, 3, 1, 1, '', self._Set_Style('Arial', 210, 1, True))
            for i in range(0, len(report_status)):
                if i%2 != 0:
                    sheet2.write(i, 2, report_status[i], self._Set_Style('Arial', 200, 63))
                else:
                    sheet2.write(i, 2, report_status[i], self._Set_Style('Arial', 200, 63, True))

            for i in range(0, len(row_status)):
                sheet2.write(5, i, row_status[i], self._Set_Style('Arial', 200, 64, True, pattern_colour=22))

            # Test Statistics Variables
            total_case = 0
            pass_case = 0
            fail_case = 0
            percentage = 1.0

            # Write test result
            for value in data:
                total_case = len(value)
                for row in range(total_case):
                    len_col = len(value[row])
                    sheet2.write(row+6, len_col,'' , self._Set_Style('Arial', 200, 64))
                    for col in range(len_col):
                        if value[row][col] == 'PASS':
                            sheet2.write(row+6, col, value[row][col], self._Set_Style('Arial', 200, 64))
                            # Pass Case
                            pass_case += 1
                        elif value[row][col] == 'FAIL':
                            sheet2.write(row+6, col, value[row][col], self._Set_Style('Arial', 200, 12, True))
                            # Fail Case
                            fail_case += 1
                        else:
                            sheet2.write(row+6, col, value[row][col], self._Set_Style('Arial', 200, 64))

            percentage = str((pass_case*100) / (fail_case+pass_case)) + '%'

            # Write Test Report

            # Cell set width of column
            col_wid1 = sheet1.col(0)
            col_wid1.width = 256*30
            col_wid1 = sheet1.col(1)
            col_wid1.width = 256*25
            col_wid1 = sheet1.col(2)
            col_wid1.width = 256*25
            col_wid1 = sheet1.col(3)
            col_wid1.width = 256*25

            # Write report name & subtitle
            sheet1.write_merge(0, 3, 0, 0, 'Test Report', self._Set_Style('Arial', 210, 17, True))
            sheet1.write_merge(0, 3, 1, 1, '', self._Set_Style('Arial', 210, 1, True))
            for i in range(0, len(report_status)):
                if i%2 != 0:
                    sheet1.write(i, 2, report_status[i], self._Set_Style('Arial', 200, 63))
                else:
                    sheet1.write(i, 2, report_status[i], self._Set_Style('Arial', 200, 63, True))

            # Record test statistics
            row_statistics = ['Total', 'Pass', 'Fail', 'Percentage']
            test_statistics = [total_case, pass_case, fail_case, percentage]
            sheet1.write_merge(5, 5, 0, 3, 'Test Statistics', self._Set_Style('Arial', 210, 64, True))
            for i in range(len(test_statistics)):
                sheet1.write(6, i, row_statistics[i], self._Set_Style('Arial', 200, 64, True, pattern_colour=22))
                sheet1.write(7, i, test_statistics[i], self._Set_Style('Arial', 200, 64))

        except IOError as e:
            print(str(e))
        finally:
            xls_file.save(filename)

    def Create_Excel_Report(self, filename):
        # Create Excel Report | ${filename}
        # reload(sys)
        # sys.setdefaultencoding('cp936')
        #sys.setdefaultencoding('utf8')
        
        file_date = 'reprot_' + datetime.datetime.now().strftime('%Y%m%d_%H%M%S') + '.xls'
        report_file = os.path.join(self.curr_dir, file_date) # Default File Path
        lines = self._Read_From_File(filename)
        data = self._Reorganize_Data(lines)
        self._Write_To_Excel_File(report_file, data)


    def open_chrome_options(self,url):
        driverOptions = webdriver.ChromeOptions()
        driverOptions.add_argument(r'user-data-dir=C:\\Users\\admin\\AppData\\Local\\Google\\Chrome\\User Data')
        driver = webdriver.Chrome(chrome_options=driverOptions)
        driver.get(url)

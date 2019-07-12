# -*- encoding = utf-8 -*-
#Author:Jacky Zhang
#Version:1.5
# Data: 2017-12-1

import json
import os
import sys
import natsort
import xlrd
import importlib

class data_center:
    def __init__(self):
        path = os.path.abspath('.')
        # Default File Path:
        self.data_dir = os.getenv('DATA_PATH', path+'\\TestData')
        # print self.data_dir

        # Current Log Path:
        self.curr_dir = os.getenv('LOG_DIR', path+'\\RobotLogs')
        # print self.curr_dir

    def Read_Excel_Data(self, filename, path=None, includeEmptyCells=True):
        """
        Returns the values from the file name specified. Returned values is separated and each sublist is one column.

        Arguments:
                |  filename (string)  | The selected Excel file that the cell values will be returned from. |
                |  path               | Default dirctory is DATA_PATH |
        Example:

        |   | *Keywords*           |  *Parameters*                                      |
        | ${data}    |  Read Excel Data           |  ExcelRobotTest.xls  |     %{DATA_PATH}      |  includeEmptyCells=True |

        """
        if path == None:
            file = os.path.join(self.data_dir, filename)  # Default File Path
        else:
            file = os.path.join(path, filename)
        try:
            data = xlrd.open_workbook(file)
            table = data.sheets()[0]

            nrows = table.nrows
            nclos = table.ncols

            listAll = []
            for row in range(2, nrows):
                alist = []
                for col in range(1, nclos):
                    val = table.cell(row, col).value
                    # Solve issue that get integer data from Excel file would be auto-changed to float type.
                    alist.append(self._keep_integer_type_from_excel(val))
                listAll.append(alist)
            # print listAll
            listAll = self._unic(listAll)
        #except Exception, e:
        #    print str(e)
        except Exception as e :
            print(str(e))

        if includeEmptyCells is True:
            return listAll
        else:
            newList = []
            for element in listAll:
                while "" in element:
                    element.remove("")
                newList.append(natsort.natsorted(element))
            OrderedData = natsort.natsorted(newList)
            return OrderedData

    def Read_Excel_Data_Cus(self, filename, srow, column, path=None, includeEmptyCells=True):
        """
        Returns the values from the file name specified. Returned values is separated and each sublist is one column.

        Arguments:
                |  filename (string)  | The selected Excel file that the cell values will be returned from. |
				|  srow               | The row number that the data begin. |
				|  column           | The column number that the data begin. |
                |  path               | Default dirctory is DATA_PATH |
        Example:

        |    | *Keywords*           |  *Parameters*                                      |
        | ${data}    |  Read Excel Data Cus           |  ExcelRobotTest.xls  |  3  |  2  |     %{DATA_PATH}      |  includeEmptyCells=True |

        """
        if path == None:
            file = os.path.join(self.data_dir, filename)  # Default File Path
        else:
            file = os.path.join(path, filename)
        try:
            data = xlrd.open_workbook(file)
            table = data.sheets()[0]

            nrows = table.nrows
            nclos = table.ncols

            listAll = []
			
            for row in range((int(srow)-1), nrows):
                alist = []
                for col in range((int(column)-1), nclos):
                    val = table.cell(row, col).value
                    # Solve issue that get integer data from Excel file would be auto-changed to float type.
                    alist.append(self._keep_integer_type_from_excel(val))
                listAll.append(alist)
            # print listAll
            listAll = self._unic(listAll)
        ##except Exception, e:
        ##    print str(e)
        except Exception as e :
            print(str(e))

        if includeEmptyCells is True:
            return listAll
        else:
            newList = []
            for element in listAll:
                while "" in element:
                    element.remove("")
                newList.append(natsort.natsorted(element))
            OrderedData = natsort.natsorted(newList)
            return OrderedData			
			
    def Read_Excel_File(self, filename, path=None, includeEmptyCells=True):
        """
        Returns the values from the file name specified. Returned values is separated and each sublist is one column.

        Arguments:
                |  filename (string)  | The selected Excel file that the cell values will be returned from. |
                |  path               | Default dirctory is DATA_PATH |
        Example:

        | | *Keywords*           |  *Parameters*                                      |
        | ${data}    |  Read Excel File           |  ExcelRobotTest.xls  |     %{DATA_PATH}      |   includeEmptyCells=True   |

        """
        if path == None:
            file = os.path.join(self.data_dir, filename)  # Default File Path
        else:
            file = os.path.join(path, filename)
        try:
            data = xlrd.open_workbook(file)
            table = data.sheets()[0]

            nrows = table.nrows
            nclos = table.ncols

            listAll = []
            for row in range(2, nrows):
                for col in range(1, nclos):
                    val = table.cell(row, col).value
                    # Solve issue that get integer data from Excel file would be auto-changed to float type.
                    value = self._keep_integer_type_from_excel(val)
                    # print value, type(value)
                    listAll.append(value)
            # print listAll
            listAll = self._unic(listAll)
        ##except Exception, e:
        ##    print str(e)
        except Exception as e :
            print(str(e))

        if includeEmptyCells is True:
            return listAll
        else:
            # Delete all empty data
            while '' in listAll:
                listAll.remove('')
            return listAll

    def _is_number(self, val):
        # Check if value is number not str.
        try:
            float(val)
            return True
        except ValueError:
            pass

        try:
            import unicodedata
            unicodedata.numeric(val)
            return True
        except (TypeError, ValueError):
            pass

    def _keep_integer_type_from_excel(self, value):
        # Keep integer number as integer type. When reading from excel it has been changed to float type.
        if self._is_number(value) and type(value) != str and value % 1 == 0:
            return str(int(value))
        else:
            return value

    def _unic(self, item):
        # Resolved Chinese mess code.
        try:
            item = json.dumps(item, ensure_ascii=False, encoding='cp936')
        except UnicodeDecodeError:
            try:
                item = json.dumps(item, ensure_ascii=False, encoding='cp936')
            except:
                pass
        except:
            pass

        item = json.loads(item, encoding='cp936')  # Convert json data string back
        return item

    def Read_Excel_Column(self, filename, column, path=None, includeEmptyCells=True):
        # reload(sys)
        # sys.setdefaultencoding('cp936')
        alist = []
        listAll = []
        if path == None:
            file = os.path.join(self.data_dir, filename)  # Default Data Directory
        else:
            file = os.path.join(path, filename)

        try:
            excel_data = xlrd.open_workbook(file)
            table = excel_data.sheets()[0]

            for row_index in range(2, table.nrows):
                value = table.cell(row_index, (int(column)-1)).value
                ##print value
                print(value)
                alist.append(self._keep_integer_type_from_excel(value))
            # print alist
            listAll = self._unic(alist)
        ##except Exception, e:
        ##    print str(e)
        except Exception as e :
            print(str(e))

        if includeEmptyCells is True:
            return listAll
        else:
            # Delete all empty data
            while '' in listAll:
                listAll.remove('')
            return listAll

    def Read_Excel_Column_Cus(self, filename, srow, column, path=None, includeEmptyCells=True):
        # reload(sys)
        # sys.setdefaultencoding('cp936')
        alist = []
        if path == None:
            file = os.path.join(self.data_dir, filename)  # Default Data Directory
        else:
            file = os.path.join(path, filename)

        try:
            excel_data = xlrd.open_workbook(file)
            table = excel_data.sheets()[0]

            for row_index in range((int(srow)-1), table.nrows):
                value = table.cell(row_index, (int(column)-1)).value
                ##print value
                print(value)
                alist.append(self._keep_integer_type_from_excel(value))
            # print alist
            listAll = self._unic(alist)
        ##except Exception, e:
        ##    print str(e)
        except Exception as e :
            print(str(e))

        if includeEmptyCells is True:
            return listAll
        else:
            # Delete all empty data
            while '' in listAll:
                listAll.remove('')
            return listAll

    def Read_Excel_Sheet(self, filename, sheetname, path=None, includeEmptyCells=True):
        """
        Returns the values from the sheet name specified.

        Arguments:
                |  Sheet Name (string)                 | The selected sheet that the cell values will be returned from.                                                              |
                |  Include Empty Cells (default=True)  | The empty cells will be included by default. To deactivate and only return cells with values, pass 'False' in the variable. |
        Example:

        |     | *Keywords*           |  *Parameters*                                      |
        | ${data}    | Read Excel Sheet           |  ExcelRobotTest.csv  | TestSheet1     |     %{DATA_PATH}         |   includeEmptyCells=True     |

        """
        if path == None:
            file = os.path.join(self.data_dir, filename)  # Default Data Directory
        else:
            file = os.path.join(path, filename)

        try:
            excel_data = xlrd.open_workbook(file)
            sheetNames = self._get_sheet_names(excel_data)
            my_sheet_index = sheetNames.index(sheetname)
            # print my_sheet_index
            table = excel_data.sheet_by_index(my_sheet_index)

            nrows = table.nrows
            nclos = table.ncols

            listAll = []
            for row in range(2, nrows):
                alist = []
                for col in range(1, nclos):
                    val = table.cell(row, col).value
                    # Solve issue that get integer data from Excel file would be auto-changed to float type.
                    alist.append(self._keep_integer_type_from_excel(val))
                listAll.append(alist)
            # print listAll
            listAll = self._unic(listAll)
        ##except Exception, e:
        ##    print str(e)
        except Exception as e :
            print(str(e))

        if includeEmptyCells is True:
            return listAll
        else:
            newList = []
            for element in listAll:
                while "" in element:
                    element.remove("")
                newList.append(natsort.natsorted(element))
            OrderedData = natsort.natsorted(newList)
            return OrderedData

    def Read_Excel_Sheet_Cus(self, filename, sheetname, srow, column, path=None, includeEmptyCells=True):
        """
        Returns the values from the sheet name specified.

        Arguments:
                |  Sheet Name (string)                 | The selected sheet that the cell values will be returned from.                                                              |
				|  srow               | The row number that the data begin. |
				|  column           | The column number that the data begin. |
                |  Include Empty Cells (default=True)  | The empty cells will be included by default. To deactivate and only return cells with values, pass 'False' in the variable. |
        Example:

        |	| *Keywords*           |  *Parameters*                                      |
        | ${data}    | Read Excel Sheet Cus           |  ExcelRobotTest.csv  | TestSheet1       |  3  |  2  |     %{DATA_PATH}         |   includeEmptyCells=True     |

        """
        if path == None:
            file = os.path.join(self.data_dir, filename)  # Default Data Directory
        else:
            file = os.path.join(path, filename)

        try:
            excel_data = xlrd.open_workbook(file)
            sheetNames = self._get_sheet_names(excel_data)
            my_sheet_index = sheetNames.index(sheetname)
            # print my_sheet_index
            table = excel_data.sheet_by_index(my_sheet_index)

            nrows = table.nrows
            nclos = table.ncols

            listAll = []
            for row in range((int(srow)-1), nrows):
                alist = []
                for col in range((int(column)-1), nclos):
                    val = table.cell(row, col).value
                    # Solve issue that get integer data from Excel file would be auto-changed to float type.
                    alist.append(self._keep_integer_type_from_excel(val))
                listAll.append(alist)
            # print listAll
            listAll = self._unic(listAll)
        ##except Exception, e:
        ##    print str(e)
        except Exception as e :
            print(str(e))

        if includeEmptyCells is True:
            return listAll
        else:
            newList = []
            for element in listAll:
                while "" in element:
                    element.remove("")
                newList.append(natsort.natsorted(element))
            OrderedData = natsort.natsorted(newList)
            return OrderedData			
			
    def _get_sheet_names(self, wb):
        """
        Returns the names of all the worksheets in the current workbook.

        Example:

        | *Keywords*              |  *Parameters*                                      |
        | ${sheetNames}           |  Get Sheets Names                                  |

        """
        sheetNames = wb.sheet_names()
        return sheetNames

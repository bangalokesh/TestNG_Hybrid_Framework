import Axe
import Validator
import json
import os
import glob
import sys

FILE_NAME_AXE = 'saved_data_axe.xlsx'
FILE_NAME_VAL = 'saved_data_val.xlsx'

LIST = []

def axelify(files):
    # delete old Axe Excel generated
    old_file = glob.glob('./www/static/' + FILE_NAME_AXE)
    if (len(old_file) > 0):
        os.remove(old_file[0])

    Axe.createNewExcelFile()
    Axe.writeHeader()

    for file_i in files:
        print("Processing " + file_i.split("/")[-1]);
        Axe.processFile(file_i)
        print(file_i.split("/")[-1] + " is processed...")

    print(str(len(files)) + " file(s) processed successfully!")


def validify(files):
    # delete old Validator Excel generated
    old_file = glob.glob('./www/static/' + FILE_NAME_VAL)
    if (len(old_file) > 0):
        os.remove(old_file[0])

    Validator.createNewExcelFile()
    Validator.writeHeader()

    for file_i in files:
        print("Processing " + file_i.split("/")[-1]);
        Validator.processFile(file_i)
        print(file_i.split("/")[-1] + " is processed...")

    print(str(len(files)) + " file(s) processed successfully!")


def main():
    path = sys.argv[1]
    files_axe = glob.glob(path + './*_AXE.json')
    files_val = glob.glob(path + './*_Validator.json')

    print(str(len(files_axe) + len(files_val)) + " will be processed!")
    axelify(files_axe)
    print("########################################")
    validify(files_val)

    print(str(len(files_axe) + len(files_val)) + " were successfully processed!")


if __name__ == '__main__':
    main()

import random

MATRIX_SIZE = 2000

DATA_FROM = -450000
DATA_TO = 600000
DECIMAL_PART = 1000 # to save matrix data as {number in range [DATA_FROM; DATA_TO]} / DECIMAL_PART
INFINITY_FORMAT = "inf"

COLS_DELIMITER = "\t"
ROWS_DELIMITER = "\n"
FILE_TO_SAVE = "matrix.txt"

matrix_data = []
for i in range(MATRIX_SIZE):
    current_row = []
    for j in range(MATRIX_SIZE):
        next_number = random.randint(DATA_FROM, DATA_TO)
        if next_number < 0:
            next_number = INFINITY_FORMAT
        else:
            next_number = str(next_number / DECIMAL_PART)
        current_row.append(next_number)
    current_str = COLS_DELIMITER.join(current_row)
    matrix_data.append(current_str)
data_str = ROWS_DELIMITER.join(matrix_data)

with open(FILE_TO_SAVE, "w") as matrix_file:
    matrix_file.write(data_str)

# Tenemos un archivo de logs que contiene errores, warnings y mensajes de información.
# Cada linea del archivo comienza con una letra que indica el tipo de log. Si
# comienza con 'E' es un error, si comienza con 'W' es un warning y si comienza
# con 'I' es de información. Luego de la letra, se encuentra un entero que
# indica el tiempo del log, y luego el mensaje del log. Excepto en el caso de
# los errores, que luego del tipo se encuentra un entero que indica la severidad
# del error. Por ejemplo:

# I 147 iniciando el programa
# W 604 this is not a warning
# E 2 4562 unexpected token

# Queremos separar los errores mas severos (con severidad mayor a 50) y ordenarlos
# cronologicamente. Despues, queremos imprimirlos a la pantalla.

import os
from collections import namedtuple

LogEntry = namedtuple('LogEntry', ['type', 'severity', 'time', 'message'])

def read_log_entries(file_path):
    with open(file_path, 'r') as file:
        for line in file:
            yield line.strip().split(' ')

def filter_severe_errors(log_entries, severity_threshold=50):
    for entry in log_entries:
        if entry[0] == 'E' and int(entry[1]) > severity_threshold:
            yield LogEntry(type=entry[0], severity=int(entry[1]), time=int(entry[2]), message=' '.join(entry[3:]))

def sort_errors_by_time(errors):
    return sorted(errors, key=lambda x: x.time)

def display_errors(errors):
    for error in errors:
        print(f"{error.type} {error.severity} {error.time} {error.message}")

def organize_log_file(error_file_path):
    try:
        log_entries = read_log_entries(error_file_path)
        severe_errors = filter_severe_errors(log_entries)
        sorted_errors = sort_errors_by_time(severe_errors)
        display_errors(sorted_errors)
    except IOError as e:
        print(f"Error reading file: {e}")

def main():
    error_file_path = './refactoring/problema2/data/error.log'
    organize_log_file(error_file_path)

if __name__ == '__main__':
    main()

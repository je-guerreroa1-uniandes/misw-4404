import os

def read_tasks(file_path):
    try:
        with open(file_path, 'r') as file:
            return [line.strip() for line in file]
    except FileNotFoundError:
        print('No existe el archivo todo.txt')
        return []

def write_tasks(file_path, tasks):
    with open(file_path, 'w') as file:
        for task in tasks:
            file.write(task + '\n')

def display_tasks(tasks):
    print('-------------------------')
    print('Tareas:')
    for i, task in enumerate(tasks):
        print(f'{i + 1}. {task}')

def add_task(tasks):
    task = input('Ingrese la tarea: ')
    tasks.append(task)
    print('Tarea agregada')

def delete_task(tasks):
    task_index = input('Ingrese el número de la tarea a eliminar: ')
    if task_index.isdigit() and (0 < int(task_index) <= len(tasks)):
        tasks.pop(int(task_index) - 1)
        print('Tarea eliminada')
    else: 
        print('No existe la tarea')

def display_menu():
    print('-------------------------')
    print('1. Ver tareas')
    print('2. Agregar una tarea')
    print('3. Eliminar una tarea')
    print('4. Salir')
    print('-------------------------')
    return input('Ingrese una opción: ')

def todo_list():
    file_path = "./refactoring/problema4/data/todo.txt"
    tasks = read_tasks(file_path)

    print('Bienvenido a su lista de tareas. Estas son sus opciones:')

    while True:
        option = display_menu()

        if option == '1':
            display_tasks(tasks)
        elif option == '2':
            add_task(tasks)
        elif option == '3':
            delete_task(tasks)
        elif option == '4':
            print('Adiós')
            break

    write_tasks(file_path, tasks)

if __name__ == '__main__':
    todo_list()

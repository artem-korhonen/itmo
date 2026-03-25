package commands;

// help : вывести справку по доступным командам
public class HelpCommand extends Command {
    public HelpCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(String[] args) {
        System.out.println("help : вывести справку по доступным командам");
        System.out.println("info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        System.out.println("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.println("insert null {element} : добавить новый элемент с заданным ключом");
        System.out.println("update id {element} : обновить значение элемента коллекции, id которого равен заданному");
        System.out.println("remove_key null : удалить элемент из коллекции по его ключу");
        System.out.println("clear : очистить коллекцию");
        System.out.println("save : сохранить коллекцию в файл");
        System.out.println("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.println("exit : завершить программу (без сохранения в файл)");
        System.out.println("remove_greater {element} : удалить из коллекции все элементы, превышающие заданный");
        System.out.println("replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого");
        System.out.println("remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        System.out.println("filter_greater_than_timezone timezone : вывести элементы, значение поля timezone которых больше заданного");
        System.out.println("print_unique_governor : вывести уникальные значения поля governor всех элементов в коллекции");
        System.out.println("print_field_descending_governor : вывести значения поля governor всех элементов в порядке убывания");
    }
}

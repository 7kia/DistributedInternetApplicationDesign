Программа обрабатывает текст порциями(названы в программе пакетами). Это сделано для того, чтобы можно было не хранить весь текст в памяти. 
Сложность полученного алгоритма: O(n).
Обработка пакетов происходит следующим образом: 
1) берётся 1 пакет и в нём ищутся слова равные 1-му искомому слову.
2) далее перебираются все пакеты от 1-го до последнего и в них ищутся слова равные 2-му искомому слову.
Пояснение к 1) и 2): 
	1) алгоритм вместо слов сохряет их индекс(не локальный(для конкретного пакета), а глобальный(какой был бы в исходном тексте))
	2) В итоге в памяти будет 2 пакета и они могут быть одинаковыми(в первом искали 1-ое слово, во 2 второе)
3) Высчитываем расстояние по индексам собранные из 2 пакетов(это может быть один и тот-же пакет) и сохряняем минимальное и максимальное расстояние
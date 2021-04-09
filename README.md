# Генератор парсеров

Упрощенный аналог генератора трансляторов.

1. Присутствует лексический анализатор.
2. Поддержка LL(1)-грамматики, нисходящий разбор.
3. Поддержка синтезируемых и наследуемых атрибутов.

Проверен на двух тестовых грамматиках: калькулятор, описание функций на языке питон.

### Грамматика входного файла:

Первая строка содержит перечисление всех атрибутов через запятую, необходимо использовать корректные Java-классы (Integer, Double, String и т.д.).
Каждое правило начинается с описания разбора:
`НЕТЕРМИНАЛ = регулярное выражение`
или
`терминал = последовательность терминалов и нетерминалов`

Далее идёт блок описания атрибутов:
```
#
Код с вычислением синтезируемых атрибутов
#S
Код с вычислением наследуемых атрибутов
#P
```
Для обращения к потомкам используется массив `node.ch[index]`, к родителю - `node.par`.

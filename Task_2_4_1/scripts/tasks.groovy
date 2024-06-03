//branchPattern = 'task-$1-$2-$3'
//folderPattern = 'Task_$1_$2_$3'
tasks {
    createTask('Heap sort', 1, 1, 1)
    createTask 'Stack', 1, 2, 1
    createTask 'Tree', 1, 2, 2
    createTask 'Graph', 1, 2, 3
    createTask 'Substring finder', 1, 3, 1
    createTask 'Record book', 1, 4, 1

    createTask('Calculator', 1, 5, 1) {
        points = 2.0
    }
    createTask 'Notebook', 1, 5, 2
    createTask('Prime numbers finder', 2, 1, 1) {
        runTests = false
    }
    createTask('Pizzeria', 2, 2, 1) {
        runTests = false
    }

    createTask('Javafx snake game', 2, 3, 1) {
        points = 2.0
    }
}
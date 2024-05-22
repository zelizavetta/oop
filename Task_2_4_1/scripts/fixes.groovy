forStudent("zelizavetta") {
    changeBranch(['Task_1_2_1': 'task-1-2-1'])
    changeFolder 'Task_1_2_1', 'Task_1_2_1'
//    changeBranchPattern 'task-$1-$2-$3'
//    changeFolderPattern 'Task_$1_$2_$3'
    changeExtraScore([Task_1_1_1: 0.1, Task_2_3_1: 0.1])
}
forStudent("a") {
    changeFolderPattern 'task-$1-$2-$3'
}
forStudent("b") {
//    changeFolderPattern 'task_$1_$2_$3'
    changeFolder 'Task_1_1_1', 'task_1_1_1_1'
    changeBranch 'Task_1_2_1', '1-2-1'
//    changeBranch 'Task_2_3_1', '2-3-1'
}
forStudent("c") {
    changeFolderPattern 'task_$1_$2_$3'
}
forStudent("d"){
    changeBranch([
            "Task_1_1_1": "task_1_1_1"
    ])
    changeFolder([
            "Task_1_1_1": "task_1_1_1__"
    ])
}
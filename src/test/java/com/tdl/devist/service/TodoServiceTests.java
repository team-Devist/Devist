package com.tdl.devist.service;


import com.tdl.devist.model.DailyCheck;
import com.tdl.devist.model.Todo;
import com.tdl.devist.model.User;
import com.tdl.devist.repository.DailyCheckRepository;
import com.tdl.devist.repository.TodoRepository;
import com.tdl.devist.testutils.UtilsForTests;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TodoServiceTests {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private DailyCheckRepository dailyCheckRepository;

    @Autowired
    private UtilsForTests utils;

    private final String TEST_USER_NAME = "dbadmin";
    private final String TEST_TODO_TITLE = "Todo 테스트하기";

    private int entitySize;

    @Test
    @Transactional
    public void 서비스_레이어에서_Todo_추가_테스트() {
        generateAndSaveTestTodoInstance(TEST_USER_NAME);

        User targetUser = userService.getUserByUserName(TEST_USER_NAME);
        List<Todo> todoList = targetUser.getTodoList();
        entitySize = todoList.size();
        Assert.assertEquals(1, entitySize);
        Todo todo = todoList.get(0);
        Assert.assertEquals(TEST_TODO_TITLE, todo.getTitle());
        Assert.assertEquals(127, todo.getRepeatDay());
    }

    @Test
    @Transactional
    public void 서비스_레이어에서_Todo_삭제_테스트() {
        generateAndSaveTestTodoInstance(TEST_USER_NAME);
        User user = userService.getUserByUserName(TEST_USER_NAME);

        List<Todo> todoList = user.getTodoList();
        Assert.assertEquals("TodoList가 비어있음.", 1, todoList.size());
        int todoId = todoList.get(0).getId();

        todoService.deleteTodo(user, todoId);

        User afterUser = userService.getUserByUserName(TEST_USER_NAME);
        Assert.assertNotNull("사용자 [" + TEST_USER_NAME + "]을/를 찾을 수 없음.", afterUser);
        List<Todo> AfterTodoList = afterUser.getTodoList();
        Assert.assertEquals(0, AfterTodoList.size());
    }

    @Test
    @Transactional
    public void 서비스_레이어에서_Todo_수정_테스트() {
        Todo todo = generateAndSaveTestTodoInstance(TEST_USER_NAME);
        User user = userService.getUserByUserName(TEST_USER_NAME);
        Assert.assertEquals(1, user.getTodoList().size());
        int todoId = user.getTodoList().get(0).getId();

        String editedTitle = "변경된 타이틀";
        todo.setTitle(editedTitle);
        todo.setRepeatCheckbox(new boolean[]{true, false, false, false, false, false, false});

        todoService.editTodo(todoId, todo);

        User afterUser = userService.getUserByUserName(TEST_USER_NAME);
        List<Todo> todoList = afterUser.getTodoList();

        Todo afterTodo = todoList.get(0);
        Assert.assertEquals(editedTitle, afterTodo.getTitle());
        Assert.assertEquals(64, afterTodo.getRepeatDay());
    }

    private Todo generateAndSaveTestTodoInstance(String username) {
        Todo todo = new Todo();
        User user = userService.getUserByUserName(username);
        todo.setTitle(TEST_TODO_TITLE);
        todo.setRepeatCheckbox(new boolean[]{true, true, true, true, true, true, true});
        todoService.addTodo(user, todo);
        userService.updateUser(user);
        return todo;
    }

    @Test
    @Transactional
    public void testSetTodoIsDoneWhenUserDo() {
        final String TODO_TITLE = "매일 하는 일";
        Todo todo = todoRepository.findByTitle(TODO_TITLE).get(0);
        utils.updatePlanedDateToToday(todo);
        Assert.assertFalse(todo.isDone());

        todoService.setTodoIsDone(todo.getId(), true);
        todo = todoRepository.findByTitle(TODO_TITLE).get(0);
        Assert.assertTrue(todo.isDone());

        todoService.setTodoIsDone(todo.getId(), false);
        todo = todoRepository.findByTitle(TODO_TITLE).get(0);
        Assert.assertFalse(todo.isDone());
    }

    @Test
    @Transactional
    public void testUpdateTodoIsDoneAfterRenewing() {
        todoService.renewTodos();

        List<Todo> todoList = todoRepository.findAll();
        for (Todo todo : todoList)
            if (todo.isTodaysTodo())
                Assert.assertFalse(todo.isDone());
    }

    @Test
    @Transactional
    public void testCreateDailyCheckAfterRenewing() {
        final String TODO_TITLE = "완료된 할 일";
        Todo doneTodo = todoRepository.findByTitle(TODO_TITLE).get(0);
        int beforeDailyCheckCount = dailyCheckRepository.findByTodo(doneTodo).size();

        todoService.renewTodos();

        doneTodo = todoRepository.getOne(doneTodo.getId());

        Assert.assertEquals(beforeDailyCheckCount + 1, dailyCheckRepository.findByTodo(doneTodo).size());

        List<DailyCheck> newDailyCheckList = dailyCheckRepository.findByTodoAndPlanedDate(doneTodo, LocalDate.now());
        Assert.assertEquals(1, newDailyCheckList.size());
    }

    @Test
    @Transactional
    public void TestCreatedDailyCheckCountWhenTodosRreRenewed() {
        List<Todo> todoList = todoRepository.findAll();
        int createdDailyChecksCount = 0;

        for (Todo todo : todoList)
            if (todo.isTodaysTodo())
                createdDailyChecksCount++;

        todoService.renewTodos();

        List<DailyCheck> dailyChecklist = dailyCheckRepository.findByPlanedDate(LocalDate.now());
        Assert.assertEquals(createdDailyChecksCount, dailyChecklist.size());
    }

    @Test
    @Transactional
    public void testUpdateDoneRateWhenUserDo() {
        final String TODO_TITLE = "Daily check 많이 가지고 있는 할 일";
        Todo todo = todoRepository.findByTitle(TODO_TITLE).get(0);
        utils.updatePlanedDateToToday(todo);

        Assert.assertEquals(40.00, todo.getDoneRate(), 00.01);

        todoService.setTodoIsDone(todo.getId(), true);
        todoService.updateDoneRate(todo.getId());

        todo = todoRepository.findByTitle(TODO_TITLE).get(0);
        Assert.assertEquals(60.00, todo.getDoneRate(), 00.01);

        todoService.setTodoIsDone(todo.getId(), false);
        todoService.updateDoneRate(todo.getId());

        todo = todoRepository.findByTitle(TODO_TITLE).get(0);
        Assert.assertEquals(40.00, todo.getDoneRate(), 00.01);

    }
}
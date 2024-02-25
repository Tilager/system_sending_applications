<template>
  <div class="container">
    <div class="card mt-5">
      <div class="card-header">
        <h4>
          Преподаватели
          <router-link to="/teachers/create" class="btn btn-primary float-end">
            Добавить преподавателя
          </router-link>
        </h4>
      </div>
      <div class="card-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>ID</th>
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Номер телефона</th>
            <th>Почта</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(teacher, idx) in teachers" :key="idx">
              <td>{{ teacher.id }}</td>
              <td>{{ teacher.surname }}</td>
              <td>{{ teacher.name }}</td>
              <td>{{ teacher.patronymic }}</td>
              <td>{{ teacher.phone }}</td>
              <td>{{ teacher.email }}</td>
              <td>
                <router-link :to="{ path: '/teachers/' + teacher.id + '/edit' }" class="btn btn-success me-1">
                  Edit
                </router-link>
                <button type="button" class="btn btn-danger" @click="deleteTeacher(teacher.id)">
                  Delete
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>

<script>
  import axios from "axios";

  export default {
    name: "teachers",
    data() {
      return {
        teachers: []
      }
    },
    mounted() {
      this.getTeachers()
    },
    methods: {
      getTeachers() {
        axios.get('http://localhost:8081/api/teachers/all').then(res => {
          this.teachers = res.data
        })
      },
      deleteTeacher(id) {
        if(confirm("Вы действительно хотите удалить данные?")) {
          axios.delete(`http://localhost:8081/api/teachers/${id}`).then(() => {
            this.getTeachers()
          })
        }
      }
    }
  }
</script>
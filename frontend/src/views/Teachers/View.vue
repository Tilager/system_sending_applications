<template>
  <div class="container">
    <div class="card">
      <div class="card-header">
        <h4>Преподаватели</h4>
      </div>
      <div class="card-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Отчество</th>
            <th>Номер телефона</th>
            <th>Почта</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(teacher, idx) in teachers" :key="idx">
              <td>{{ teacher.id }}</td>
              <td>{{ teacher.name }}</td>
              <td>{{ teacher.surname }}</td>
              <td>{{ teacher.patronymic }}</td>
              <td>{{ teacher.phone }}</td>
              <td>{{ teacher.email }}</td>
              <td>
                <router-link to="/" class="btn btn-success">
                  Edit
                </router-link>
                <button type="button" class="btn btn-danger">
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
        axios.get('http://localhost:8081/api/allTeachers').then(res => {
          this.teachers = res.data
          console.log(this.teachers)
        })
      }
    }
  }
</script>
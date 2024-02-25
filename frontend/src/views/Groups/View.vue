<template>
  <div class="container">
    <div class="card mt-5">
      <div class="card-header">
        <h4>
          Курсы
          <router-link to="/courses/create" class="btn btn-primary float-end">
            Добавить курс
          </router-link>
        </h4>
      </div>
      <div class="card-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Описание</th>
            <th>Язык</th>
            <th>Длительность (Ч)</th>
            <th>Цена</th>
            <th>Преподаватель</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(course, idx) in courses" :key="idx">
              <td>{{ course.id }}</td>
              <td>{{ course.name }}</td>
              <td>{{ course.description }}</td>
              <td>{{ course.language }}</td>
              <td>{{ course.duration }}</td>
              <td>{{ course.price }}</td>
              <td>{{ course.teacher.surname }} {{ course.teacher.name }} {{ course.teacher.patronymic }}</td>
              <td>
                <router-link
                    :to="{ path: '/courses/' + course.id + '/edit' }"
                    class="btn btn-success me-1"
                >
                  Edit
                </router-link>
                <button type="button" class="btn btn-danger" @click="deleteCourse(course.id)">
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
    name: "courses",
    data() {
      return {
        courses: [],
        teachers: []
      }
    },
    mounted() {
      this.getCourses()
      axios.get('http://localhost:8081/api/teachers/all').then(res => {
        this.teachers = res.data
      })
    },
    methods: {
      getCourses() {
        axios.get('http://localhost:8081/api/courses/all').then(res => {
          this.courses = res.data
        })
      },
      deleteCourse(id) {
        if(confirm("Вы действительно хотите удалить данные?")) {
          axios.delete(`http://localhost:8081/api/courses/${id}`).then(() => {
            this.getCourses()
          })
        }
      }
    }
  }
</script>
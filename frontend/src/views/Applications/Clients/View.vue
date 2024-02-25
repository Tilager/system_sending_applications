<template>
  <div class="container">
    <div class="card mt-5">
      <div class="card-header">
        <h4>
          Заявки
        </h4>
      </div>
      <div class="card-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>ID</th>
            <th>Статус заявки</th>
            <th>Дата заявки</th>
            <th>Клиент</th>
            <th>Курс</th>
            <th>Группа</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(application, idx) in applications" :key="idx">
              <td>{{ application.id }}</td>
              <td>{{ this.status[application.status] }}</td>
              <td>{{ application.submissionDate }}</td>
              <td>{{ application.client.surname }} {{application.client.name}} {{ application.client.patronymic }}</td>
              <td>{{ application.course.name }} ({{application.course.language}})</td>
              <td v-if="application.group">{{ application.group.name }}</td>
              <td v-else></td>
              <td>
                <router-link
                    :to="{ path: '/applications/' + application.id + '/edit' }"
                    class="btn btn-success me-1">
                  Edit
                </router-link>
                <button type="button" class="btn btn-danger" @click="deleteApplication(application.id)">
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
    name: "applications",
    data() {
      return {
        applications: [],
        status: {
          consideration: 'На рассмотрении',
          access: 'Принято',
          rejected: 'Отклонено'
        }
      }
    },
    mounted() {
      this.getApplications()
    },
    methods: {
      getApplications() {
        axios.get('http://localhost:8081/api/applications/all').then(res => {
          this.applications = res.data
        })
      },
      deleteApplication(id) {
        if(confirm("Вы действительно хотите удалить данные?")) {
          axios.delete(`http://localhost:8081/api/applications/${id}`).then(() => {
            this.getApplications()
          })
        }
      }
    }
  }
</script>
<template>
  <div class="container">
    <div class="card mt-5">
      <div class="card-header">
        <h4>
          Группы
          <router-link to="/groups/create" class="btn btn-primary float-end">
            Добавить группу
          </router-link>
        </h4>
      </div>
      <div class="card-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Вместимость</th>
            <th>Курс</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(group, idx) in groups" :key="idx">
              <td>{{ group.id }}</td>
              <td>{{ group.name }}</td>
              <td>{{ group.capacity }}</td>
              <td>{{ group.course.name }} ({{group.course.language}})</td>
              <td>
                <router-link
                    :to="{ path: '/groups/' + group.id + '/edit' }"
                    class="btn btn-success me-1"
                >
                  Edit
                </router-link>
                <button type="button" class="btn btn-danger" @click="deleteGroup(group.id)">
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
    name: "groups",
    data() {
      return {
        groups: []
      }
    },
    mounted() {
      this.getGroups()
    },
    methods: {
      getGroups() {
        axios.get('http://localhost:8081/api/groups/all').then(res => {
          this.groups = res.data
        })
      },
      deleteGroup(id) {
        if(confirm("Вы действительно хотите удалить данные?")) {
          axios.delete(`http://localhost:8081/api/groups/${id}`).then(() => {
            this.getGroups()
          })
        }
      }
    }
  }
</script>
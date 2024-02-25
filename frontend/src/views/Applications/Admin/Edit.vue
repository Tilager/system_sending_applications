<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header">
        Изменение заявки
      </div>
      <div class="card-body">
        <div class="alert alert-success" role="alert" v-if="alert === 'success'">
          Данные успешно обновлены!
        </div>
        <div class="alert alert-danger" role="alert" v-if="alert === 'danger'">
          {{alertText}}
        </div>

        <div class="mb-3">
          <label class="mb-3">Статус</label>
          <select v-model="model.application.status" class="form-select">
            <option value="consideration">На рассмотрении</option>
            <option value="access">Принято</option>
            <option value="rejected">Отклонено</option>
          </select>
        </div>

        <div class="mb-3">
          <label class="mb-3">Группа</label>
          <select class="form-select" @change="setGroup" :disabled="model.application.status !== 'access'">
            <option disabled
                    :selected="model.application.group === null" value> Выберите группу </option>
            <option v-for="group in groups"
                    :value="group.id"
            > {{ group.name }} </option>
          </select>
        </div>

        <div class="mb-3">
          <button class="btn btn-primary" @click="updateApplication" type="button">Сохранить</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  import useVuelidate from "@vuelidate/core";

  export default {
    name: 'applicationsEdit',
    data() {
      return {
        alert: '',
        alertText: '',
        model: {
          application: {
            status: '',
            submissionDate: null,
            client: null,
            course: null,
            group: null
          }
        },
        groups: []
      }
    },
    async mounted() {
      this.getApplication(this.$route.params.id)
    },
    methods: {
      setGroup(event) {
        this.model.application.group = this.groups.find(g => g.id == event.target.value)
      },
      getApplication(id) {
        axios.get(`http://localhost:8081/api/applications/${id}`).then(res => {
          if (!res.data) {
            alert("Страница не найдена!")
            window.location.replace("/applications")
          }
          else {
            this.model.application = res.data
            axios.get(`http://localhost:8081/api/groups/allByCourse/${res.data.course.id}`).then(res2 => {
              this.groups = res2.data
            })
          }
        })
      },
      updateApplication() {
        if (this.v$.$invalid) {
          this.v$.$touch()
          this.alert = 'danger'
          this.alertText='Проверьте все поля!'
          return
        }

        if (this.model.application.status !== 'access')
          this.model.application.group = null

        axios.post(`http://localhost:8081/api/applications/${this.$route.params.id}`,
            this.model.application)
            .then(res => {
              this.model.application = res.data
              this.alert = 'success'
              this.v$.$reset()
            }).catch(err => {
                if(err.response.status === 409) {
                  this.alert = 'danger'
                  this.alertText = err.response.data
                }
                else {
                  console.error(err.response.data)
                }
            })
      }
    },
    setup: () => ({ v$: useVuelidate() })
  }
</script>

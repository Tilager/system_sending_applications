import {createStore} from "vuex";

export default createStore(
    {
        state: {
            user: JSON.parse(localStorage.getItem('user'))
        },
        getters: {
            currentUser(state){
                return state.user
            }
        },
        mutations: {
            setUserMt(state, user) {
                state.user = user
            }
        },
        actions: {
            async setUser(ctx, user){
                localStorage.setItem('user', JSON.stringify(user))
                ctx.commit('setUserMt', user)
            },
            async clearUser(ctx) {
                localStorage.removeItem('user')
                ctx.commit('setUserMt', null)
            }
        }
    }
)
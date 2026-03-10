import { ViewConfig } from "@vaadin/hilla-file-router/types.js"
import { useForm } from "@vaadin/hilla-react-form"
import { Button, Checkbox, CheckboxGroup, ComboBox, FormLayout, Notification, Select, TextField, VerticalLayout } from "@vaadin/react-components"
import KeycloakUserRecord from "Frontend/generated/com/example/application/data/KeycloakUserRecord"
import Role from "Frontend/generated/com/example/application/data/Role"
import UserAppModel from "Frontend/generated/com/example/application/data/UserAppModel"
import { RolesService, UserAppService, UserKeycloakService } from "Frontend/generated/endpoints"
import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router"

export const config: ViewConfig = {
    rolesAllowed: ['ROLE_ADMIN'],
    menu: {exclude: true},
    title: 'User Application Edit',
}
export default function UserEdit() {
    const {username} = useParams()
    const navigate = useNavigate()
    const form = useForm(UserAppModel,{
      onSubmit: async (userApp) => {
        console.log(userApp)
        await UserAppService.save(userApp).then(result => {
          Notification.show(`${result.username} telah disimpan`)
          navigate(`/assign_role`)
        })
      }
    })
    const [roles,setRoles] = useState<string[]>([])
    const [usernames,setUsernames] = useState<KeycloakUserRecord[]>([])
    useEffect(()=> {
       const fetchData = async ()=>{
        await UserAppService.findbyUsername(username).then(form.read)
        await RolesService.getRoles().then(roles => setRoles(roles))
        await UserKeycloakService.getKeycloakUserRecords().then(results => {
                    setUsernames(results)
                    console.log(usernames)
                  })
       }
       fetchData()
    },[])
  return (
    <FormLayout style={{ width: '100%' }} autoResponsive labelsAside>
       <Select items={usernames} {...form.field(form.model.username)} readonly />
       <TextField {...form.field(form.model.description)} label={'Description'}  />
       <CheckboxGroup {...form.field(form.model.roles)}>
            {
            roles.map((value,index) => (
                <Checkbox value={value} key={index} label={value}  />
            ))
            }
        </CheckboxGroup>
        <Button onClick={form.submit}>Simpan</Button>
    </FormLayout>
  )
}

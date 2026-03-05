import { ViewConfig } from "@vaadin/hilla-file-router/types.js"
import { useSignal } from "@vaadin/hilla-react-signals"
import { Button, Notification } from "@vaadin/react-components"
import { AdminService } from "Frontend/generated/endpoints"

export const config: ViewConfig = {
    rolesAllowed: ['ROLE_ADMIN'],
    menu: {
        title: 'Admin'
    }
}   

export default function AdminIndex() {

    return(
        <>
            <h1>Admin Page</h1>
            <Button onClick={async ()=> {
                 await AdminService.tesAdmin().then(data => Notification.show(data));
                 
            }}>Tes</Button>
        </>
    )
}
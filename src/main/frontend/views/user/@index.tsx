import { ViewConfig } from "@vaadin/hilla-file-router/types.js"
import { Button, Notification } from "@vaadin/react-components";
import { AdminService, UserService } from "Frontend/generated/endpoints";

export const config: ViewConfig = {
    rolesAllowed: ['ROLE_USER'],
     menu: {
        title: 'User'
    }
}

export default function UserIndex() {
    return(
        <>
            <h1>User Page</h1>
             <Button onClick={async ()=> {
                 const tes = await UserService.tesAdmin();
                 Notification.show(tes)
            }}>Tes</Button>
        </>
    )
}
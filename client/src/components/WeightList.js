import React from 'react';
import { ListGroup, ListGroupItem } from 'react-bootstrap';

const WeightList = (props) => {
    //console.log(props.data[0]);
    return (
        <ListGroup>
            {
                props.data != null ? props.data.map(
                    obj =>
                    <ListGroupItem
                        key={obj.id}
                    >
                        {obj.date} : {obj.weight}
                    </ListGroupItem>
                ) : null
            }
        </ListGroup>
    );
}

export default WeightList;

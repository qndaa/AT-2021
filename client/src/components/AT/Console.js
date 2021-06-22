import React from "react";

class Console extends React.Component {

    constructor(props) {
        super(props);
        this.state = {consoleContent: ''};
    }

    componentDidMount = () => {

        let consoleId;


        try {
            let socket = new WebSocket("ws://" + window.location.hostname +":8080/ChatWAR/ws/console");

            localStorage.getItem('consoleId') == null ? consoleId = '' : consoleId = localStorage.getItem('consoleId');


            socket.onopen = function(){
                console.log('onopen: Socket Status: '+socket.readyState+' (open)');
                //localStorage.setItem('isLogin', 'FALSE');
            }

            socket.onmessage = (msg) => {
                console.log(msg.data);
                if (msg.data.startsWith('CONSOLE&RELOAD')) {
                    this.props.reload();
                } else if (msg.data.startsWith('CONSOLE&')) {
                    const cont = msg.data.split('&')[1];
                    this.setState({consoleContent: this.state.consoleContent + new Date().toLocaleTimeString() + '---' + cont});

                }


                else {
                    console.log('onmessage console: Received: '+ msg.data);
                    consoleId = msg.data.split(":")[1];
                    localStorage.setItem('consoleId', consoleId);
                    console.log('Id saved in local storage - id:' + consoleId);
                }




            }
            socket.onclose = function(){
                socket = null;
                localStorage.removeItem('consoleId');
            }

        } catch(exception){
            console.log('Error'+ exception);
        }



    }

    render() {
        return (
            <div className={`mt-5`}>
                <div>
                    <h4 className={`text-dark font-weight-bold mb-3`}>Console:</h4>
                </div>
                <div>
                    <textarea id="consoleLog" style={{height: 200}} className={`w-100`} value={this.state.consoleContent} readOnly={true} autoFocus={true}/>
                </div>
            </div>
        );
    }

}

export default Console;

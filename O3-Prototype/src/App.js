import React from 'react';
import { AppBar, Typography, Toolbar, Container, TextField, Card, CardContent, Select, MenuItem, Button, Table, TableHead, TableRow, TableCell, TableBody, Checkbox, TextareaAutosize } from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord';
import './App.css';
import axios from 'axios';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			rows: [{
				date: "",
				meetingDone: "",
				meetingDate: "",
				comments: "",
				status: "",
			}],
			supervisee: '',
			existingDetails: [],
			superviseeList: [],
			response: null
		};
	}


	componentDidMount() {
		this.getSupervisseList();
		// this.getO3Details();
	}

	getSupervisseList() {
		const response = axios.get('http://localhost:8080/api/supervisee')
			.then(res => {
				const response = res.data;
				this.setState({ superviseeList: response });
			})
	}

	getO3SuperviseeDetails() {
		const response = axios.get('http://localhost:8080/api/o3SuperviseeDetails')
			.then(res => {
				const response = res.data;
				this.setState({ existingDetails: response });
			})
		// const existingData = {
		// 	date: "06/10/2020",
		// 	meetingDone: true,
		// 	meetingDate: "06/10/2020",
		// 	comments: "Testing",
		// 	status: "Green",
		// }
		// let existingDetails = this.state.existingDetails;
		// existingDetails.push(existingData);
		// this.setState({ existingDetails })
	}

	addRow = () => {
		let rows = this.state.rows;
		rows.push({
			date: "",
			meetingDone: "",
			meetingDate: "",
			comments: "",
			status: "",
		});
		this.setState({ rows });
	}

	deleteRow = i => {
		let rows = this.state.rows;
		if (rows.length > 1) {
			rows.splice(i, 1);
			this.setState({ rows });
		}
	}


	submitValues = () => {
		alert('An O3 was submitted: ' + JSON.stringify(this.state));
		const response = axios.post('url', this.state);
		this.setState({ response: response.data });
	}



	handleChange(i, e) {
		const { name, value } = e.target;
		let rows = [...this.state.rows];
		rows[i] = { ...rows[i], [name]: value };
		this.setState({ rows });
	}
	setSupervisee = e => {
		this.setState({ supervisee: e.target.value });
		this.getO3SuperviseeDetails();
	}

	createUI() {
		return this.state.rows.map((row, i) => <TableRow key={i}>
			<TableCell><TextField type='date' name='date' value={row.date} onChange={this.handleChange.bind(this, i)} /></TableCell>
			<TableCell><Checkbox color='primary' name='meetingDone' value={row.meetingDone} onChange={this.handleChange.bind(this, i)} /></TableCell>
			<TableCell>	<TextField type='date' name='meetingDate' value={row.meetingDate} onChange={this.handleChange.bind(this, i)} /></TableCell>
			<TableCell><TextareaAutosize name='comments' value={row.comments} onChange={this.handleChange.bind(this, i)} /></TableCell>
			<TableCell>
				<Select name='status' value={row.status} onChange={this.handleChange.bind(this, i)} style={{ minWidth: 120 }}>
					<MenuItem value='red'>Red</MenuItem>
					<MenuItem value='green'>Green</MenuItem>
					<MenuItem value='amber'>Amber</MenuItem>
				</Select></TableCell>
			<TableCell>
				{this.state.rows.length === 1 ? '' : <Button variant='contained' onClick={e => this.deleteRow(i)}> <DeleteIcon /></Button>}
			</TableCell>
		</TableRow>)
	}
	existingDetail() {
		return this.state.existingDetails.map((row, i) => <TableRow key={i}>
			<TableCell><TextField type='text' name='gender' value={row.gender} onChange={this.handleChange.bind(this, i)} /></TableCell>
			<TableCell>
				{this.state.existingDetails.length === 1 ? '' : <Button variant='contained' onClick={e => this.deleteRow(i)}> <DeleteIcon /></Button>}
			</TableCell>
		</TableRow>)

	}

	o3DetailUI() {
		return this.state.existingDetails.map((row, i) => <TableRow key={i}>
			<TableCell><label />{row.date}</TableCell>
			<TableCell>{row.meetingDone ? <label>Yes</label> : <label>No</label>}</TableCell>
			<TableCell><label />{row.meetingDate}</TableCell>
			<TableCell><label />{row.comments}</TableCell>
			<TableCell >
				<svg height="50" width="100">
					<circle cx="50" cy="30" r="10" stroke-width="3" fill="green" />
				</svg>

			</TableCell>
			<TableCell>
				<Button variant='contained' onClick={e => this.editO3Detail(i)}> <EditIcon /></Button>
			</TableCell>
		</TableRow>)
	}

	render() {
		const { supervisee } = this.state
		return (
			<div className='App'>
				<AppBar position='static'>
					<Toolbar>
						<Typography variant='h6'>O3 App</Typography>
					</Toolbar>
				</AppBar>
				<Container maxWidth='lg' style={{ marginTop: 16 }}>
					<Typography variant='h2'>{this.state.supervisee}  O3</Typography>
					<Card variant='outlined'>
						<CardContent style={{ textAlign: 'left' }}>Choose Supervisee
							<Button style={{ marginLeft: '91%' }} variant='contained' color='primary' onClick={this.addRow}>
								<AddIcon />
							</Button>

							{/* {this.superviseeDetail} */}
							<Select name='supervisee' value={supervisee} onChange={this.setSupervisee} style={{ minWidth: 120 }} >
								{this.state.superviseeList.map((item, i) =>
									<MenuItem key={item} value={item}>{item}</MenuItem>
								)}
							</Select>
							{/* <Select name='status' value={supervisee} onChange={this.setSupervisee} style={{ minWidth: 120 }}>
									<MenuItem value='Puneet'>Puneet</MenuItem>
									<MenuItem value='Depti'>Depti</MenuItem>
									<MenuItem value='Dheeraj'>Dheeraj</MenuItem>
							</Select> */}
						</CardContent>
						<CardContent style={{ textAlign: 'left' }}>
							<Table>
								<TableHead>
									<TableRow>
										<TableCell>O3 Week</TableCell>
										<TableCell>Meeting Done?</TableCell>
										<TableCell>Meeting Date</TableCell>
										<TableCell>Discussion Comments</TableCell>
										<TableCell>Status of O3</TableCell>
										<TableCell>Edit</TableCell>
									</TableRow>
								</TableHead>
								<TableBody>
									{this.createUI()}
								</TableBody>
							</Table>
						</CardContent>
						<CardContent>
							<Button style={{ marginLeft: '91%' }} color='primary' variant='contained' onClick={this.submitValues}>Submit</Button>
						</CardContent>
						<CardContent style={{ textAlign: 'left' }}>
							<Table>
								<TableHead>
									<TableRow>
										<TableCell>O3 Week</TableCell>
										<TableCell>Meeting Done?</TableCell>
										<TableCell>Meeting Date</TableCell>
										<TableCell>Discussion Comments</TableCell>
										<TableCell>Status of O3</TableCell>
										<TableCell>Edit</TableCell>
									</TableRow>
								</TableHead>
								<TableBody>
									{this.o3DetailUI()}
								</TableBody>
							</Table>
						</CardContent>
					</Card>
				</Container>
			</div>
		);
	}
}

export default App;